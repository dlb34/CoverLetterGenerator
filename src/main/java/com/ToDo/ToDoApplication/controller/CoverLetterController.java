
package com.ToDo.ToDoApplication.controller;

import com.ToDo.ToDoApplication.model.CoverLetterRequest;
import com.ToDo.ToDoApplication.service.CoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CoverLetterController {

    @Autowired
    private CoverLetterService coverLetterService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateCoverLetter(@RequestBody CoverLetterRequest request) {
        try {
            String letter = coverLetterService.generateLetter(request);
            return ResponseEntity.ok(letter);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/upload-cv")
    public ResponseEntity<String> uploadCV(@RequestParam("file") MultipartFile file) {
        // Placeholder for file parsing
        return ResponseEntity.ok("Parsed CV content here...");
    }

    @GetMapping("/company-info")
    public ResponseEntity<String> getCompanyInfo(@RequestParam String name) {
        // Placeholder for scraping or API call
        return ResponseEntity.ok("Company info for: " + name);
    }
}
