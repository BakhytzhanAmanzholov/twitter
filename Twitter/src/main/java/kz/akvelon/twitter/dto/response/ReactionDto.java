package kz.akvelon.twitter.dto.response;

import kz.akvelon.twitter.model.ReactionInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionDto {
    private Long id;
    private String name;
    private int count;

    public static ReactionDto from(ReactionInfo reactionInfo){
        return ReactionDto.builder()
                .id(reactionInfo.getId())
                .name(reactionInfo.getReaction().getName())
                .count(reactionInfo.getCount())
                .build();
    }
}
