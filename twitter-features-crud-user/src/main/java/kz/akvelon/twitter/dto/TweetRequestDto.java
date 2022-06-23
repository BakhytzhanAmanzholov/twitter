package kz.akvelon.twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetRequestDto {
    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime dateTime;
}
