package minskim2.JHP_World.domain.comment.dto;

import lombok.Builder;

public class CommentReq {

    @Builder
    public record CreateReq(
            Long postId,
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
