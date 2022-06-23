package kz.akvelon.twitter.model;

import kz.akvelon.twitter.dto.request.RegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    private String username;
    private String password;

    public static AccountTemp fromRequestDto(RegistrationDto registrationDto){
        return AccountTemp.builder()
                .email(registrationDto.getEmail())
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .build();
    }
}
