package kz.akvelon.twitter.dto.response.users;

import kz.akvelon.twitter.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String username;
    private Integer subscribers;
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
