package com.ToDo.ToDoApplication.utils;

import com.ToDo.ToDoApplication.model.CoverLetterRequest;

public class Utils {

    public static String createPrompt(CoverLetterRequest req, String aboutText){
        return String.format("""
                        Generate a cover letter for the following job application.

                        Name: %s
                        Location: %s
                        Email: %s
                        Phone: %s
                        Date: %s

                        Company: %s
                        Job Title: %s

                        Job Description:
                        %s

                        Resume:
                        %s

                        Company Info:
                        %s

                        The tone should be professional but enthusiastic, tailored to the role and a bit about company (e.g. how values are in line with mine).
                        Format it as a proper UK-style cover letter with no markdown.
                                
                        An example of a good length and CV is this one for spotifys backend software engineer role: 
                                
                                        Firstname Lastname
                                        Location
                                        email@email.com
                                        mobilenumber
                                        October 15, 2024
                                        Hiring Manager
                                        Spotify
                                        London, United Kingdom
                                        Dear Hiring Manager,
                                        I am thrilled to apply for the Junior Backend Engineer position within the Content Catalog team at Spotify. With a solid foundation in backend development using Java Spring Boot, alongside experience in cloud technologies and data management, I am eager to contribute delivering exceptional audio experiences to millions worldwide.
                                        At "lastcompanyname", I have honed my backend engineering skills by leading the development of critical services, including a mortgage journey digitalization project. Here, I leveraged Java Spring Boot, MongoDB, and Kafka to create scalable, reliable microservices that significantly improved user experience. My role involved collaborating closely with cross-functional teams to ensure that our solutions were technically sound and aligned with user needs. This aligns perfectly with Spotify's collaborative spirit, where every team member plays a vital role in achieving shared goals.
                                        Spotify's emphasis on a playful and passionate culture resonates deeply with me. I strive to approach challenges with enthusiasm and creativity, inspiring others to do the same. As co-founder of the xxx Group at lastcompanyname, I actively promoted an environment where engineers could share ideas, celebrate successes, and learn from one another.\s
                                        Thank you for considering my application. I look forward to the opportunity to discuss how my skills can contribute to the continued success at Spotify.
                                        Warm regards,
                                        Firstname Lastname
                                
                        """, req.getName(), req.getLocation(), req.getEmail(), req.getPhone(), req.getDate(),
                req.getCompany(), req.getJobTitle(), req.getJobDescription(),
                req.getCvText(), aboutText);
    }
}
