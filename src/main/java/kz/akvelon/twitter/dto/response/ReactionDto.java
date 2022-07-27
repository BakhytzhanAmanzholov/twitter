package kz.akvelon.twitter.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.akvelon.twitter.model.ReactionInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "Reaction with its name and amount")
public class ReactionDto {
    @Schema(name = "ID of a reaction", example = "1")
    private Long id;
    @Schema(name = "Name of reaction", example = "SMILE")
    private String name;
    @Schema(name = "Count of a reaction", example = "95")
    private int count;

    public static ReactionDto from(ReactionInfo reactionInfo){
        return ReactionDto.builder()
                .id(reactionInfo.getId())
                .name(reactionInfo.getReaction().getName())
                .count(reactionInfo.getCount())
                .build();
    }
}
