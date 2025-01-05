package minskim2.JHP_World.domain.comment.dto;

import lombok.Builder;

public class CommentReq {

    @Builder
    public record create (
            Long id,
            Long memberId,
            Long postId,
            String body
    ) {
    }

    @Builder
    public record update (
            Long id,
            String body
    ) {
    }

    @Builder
    public record delete (
            Long id
    ) {
    }
}
