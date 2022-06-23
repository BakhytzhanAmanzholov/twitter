package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetsDtoPage {
    private List<TweetResponseDto> tweets;
    private Integer totalPages;
    private Integer size;
}
