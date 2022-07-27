package kz.akvelon.twitter.dto.response.users;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.akvelon.twitter.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User information response")
public class UserInfoDto {
    @Schema(name = "Tweet id", example = "1")
    private Long id;
    @Schema(name = "Email of a user", example = "email@gmail.com")
    private String email;
    @Schema(name = "Username of a user", example = "clark.jake")
    private String username;
    @Schema(name = "Amount of subscribers", example = "10")
    private Integer subscribers;
    @Schema(name = "Amount of subscriptions", example = "15")
    private Integer subscriptions;

    public static UserInfoDto from(Account account){
        return UserInfoDto.builder()
                .id(account.getId())
                .subscribers(account.getSubscribers())
                .subscriptions(account.getSubscriptions())
                .email(account.getEmail())
                .username(account.getUsername())
                .build();
    }
}
