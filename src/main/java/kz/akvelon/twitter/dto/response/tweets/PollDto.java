package kz.akvelon.twitter.dto.response.tweets;

import kz.akvelon.twitter.model.Poll;
import kz.akvelon.twitter.model.PollChoice;
import kz.akvelon.twitter.model.Tweet;
import lombok.Builder;
import lombok.Data;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PollDto {
    private Long id;
    private Date dateTime;
    private List<String> pollChoices;

    public static PollDto from(Poll poll){
        PollDto dto = PollDto.builder()
                .id(poll.getId())
                .pollChoices(new ArrayList<>())
                .dateTime(poll.getDateTime())
                .build();
        for (PollChoice pollChoice: poll.getPollChoices()){
            dto.getPollChoices().add(pollChoice.getChoice());
        }
        return dto;
    }
}
