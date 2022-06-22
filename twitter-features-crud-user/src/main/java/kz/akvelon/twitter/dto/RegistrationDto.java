package kz.akvelon.twitter.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String username;
    private String password;
}
