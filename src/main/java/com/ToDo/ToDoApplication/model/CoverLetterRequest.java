
package com.ToDo.ToDoApplication.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoverLetterRequest {
    private String name;
    private String location;
    private String email;
    private String phone;
    private String date;
    private String company;
    private String jobTitle;
    private String jobDescription;
    private String cvText;
    private String optionalAboutText;
    private String companyUrl;

}
