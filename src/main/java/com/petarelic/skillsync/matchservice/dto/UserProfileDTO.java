package com.petarelic.skillsync.matchservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    @NotBlank
    private String username;
    @Email
    private String email;
    private String bio;
    private String avatarUrl;
}
