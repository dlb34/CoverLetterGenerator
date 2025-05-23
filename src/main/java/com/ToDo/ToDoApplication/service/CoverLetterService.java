package com.ToDo.ToDoApplication.service;

import com.ToDo.ToDoApplication.model.CoverLetterRequest;
import com.ToDo.ToDoApplication.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class CoverLetterService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String generateLetter(CoverLetterRequest request) throws Exception {
        String prompt = buildPrompt(request);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", new Object[] {
                        Map.of("role", "system", "content", "You are a professional cover letter writer."),
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", 0.7
        );

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> result = mapper.readValue(response.body(), Map.class);

        System.out.println("OpenAI raw response: " + response.body());

        // ✅ Add defensive checks
        Object choicesObj = result.get("choices");
        if (!(choicesObj instanceof java.util.List<?> choicesList) || choicesList.isEmpty()) {
            throw new IllegalStateException("OpenAI response missing 'choices' array or it's empty");
        }

        Object firstChoiceObj = choicesList.get(0);
        if (!(firstChoiceObj instanceof Map<?, ?> firstChoiceMap)) {
            throw new IllegalStateException("First choice is not a valid map");
        }

        Object messageObj = firstChoiceMap.get("message");
        if (!(messageObj instanceof Map<?, ?> messageMap)) {
            throw new IllegalStateException("Missing 'message' field in OpenAI response");
        }

        Object contentObj = messageMap.get("content");
        if (!(contentObj instanceof String content)) {
            throw new IllegalStateException("Missing 'content' in OpenAI response message");
        }

        return content;
    }


    private String buildPrompt(CoverLetterRequest req) {
        String aboutText = req.getOptionalAboutText();
        System.out.println(aboutText);
        if ((aboutText == null || aboutText.isBlank()) && req.getCompanyUrl() != null) {
            aboutText = scrapeCompanyInfo(req.getCompanyUrl());
        }

        return Utils.createPrompt(req, aboutText);
    }

    private String scrapeCompanyInfo(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();
            return doc.body().text();
        } catch (Exception e) {
            return "Could not scrape company info from URL: " + url;
        }
    }

}

