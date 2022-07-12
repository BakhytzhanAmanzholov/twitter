package kz.akvelon.twitter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetRequestDto {
    private String text;
    private Long pollDateTime;
    private List<String> choices;
}
