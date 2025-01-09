package minskim2.JHP_World.domain.comment.dto;

import lombok.Builder;
import minskim2.JHP_World.domain.comment.entity.Comment;

public class CommentRes {

    @Builder
    public record CreateRes (
        Long id
    ) {
        public static CreateRes of(Long id) {
            return new CreateRes(id);
        }
        public static CreateRes from(Comment comment) {
            return new CreateRes(comment.getId());
        }
    }

    @Builder
    public record GetRes (
        Long id,
        Long memberId,
        String memberName,
        String body
    ) {
        public static GetRes from(Comment comment) {
            return new GetRes(comment.getId(), comment.getMember().getId(), comment.getMember().getName(), comment.getBody());
        }
    }

    @Builder
    public record UpdateRes (
        Long id,
        String body
    ) {
        public static UpdateRes of(Long id, String body) {
            return new UpdateRes(id, body);
        }
    }

    @Builder
    public record DeleteRes (
        Long id
    ) {
    }
}
