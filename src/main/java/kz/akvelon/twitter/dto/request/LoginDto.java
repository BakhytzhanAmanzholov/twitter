package kz.akvelon.twitter.dto.request;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
