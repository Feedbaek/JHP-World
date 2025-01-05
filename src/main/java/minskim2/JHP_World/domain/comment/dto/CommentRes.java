package minskim2.JHP_World.domain.comment.dto;

import lombok.Builder;
import lombok.Data;

public class CommentRes {

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
