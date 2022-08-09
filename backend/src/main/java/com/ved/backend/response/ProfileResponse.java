package com.ved.backend.response;

import com.ved.backend.model.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProfileResponse {
    
    private String username;

    private String displayUrl;

    private String firstname;

    private String lastname;

    private String biography;

    private String occupation;

    public ProfileResponse(Student student) {
        this.username = student.getAppUser().getUsername();
        this.displayUrl = student.getProfilePicUri();
        this.firstname = student.getFirstName();
        this.lastname = student.getLastName();
        this.biography = student.getBiography();
        this.occupation = student.getOccupation();
    }

}