package minskim2.JHP_World.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class CommentReq {

    @Builder
    public record CreateReq(
            @NotNull
            Long postId,
            @NotBlank
            String body
    ) {
    }

    @Builder
    public record UpdateReq(
            Long id,
            String body
    ) {
    }

    @Builder
    public record DeleteReq(
            Long id
    ) {
    }
}
