package minskim2.JHP_World.domain.post.dto;

import minskim2.JHP_World.domain.post.entity.Post;

public class PostRes {

    public record CreateRes(
            Long id
    ) {
        public static CreateRes of(Long id) {
            return new CreateRes(id);
        }
    }

    public record GetPreviewRes(
            Long id,
            String title,
            String body
    ) {
        public static GetPreviewRes from(Post post) {
            return new GetPreviewRes(post.getId(), post.getTitle(), post.getBody());
        }
    }

    public record UpdateRes(
            Long id,
            String title,
            String body
    ) {
    }

    public record DeleteRes(
            Long id
    ) {
    }
}
