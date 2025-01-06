package minskim2.JHP_World.domain.post.dto;

public class PostReq {

    public record CreateReq(
            Long lectureId,
            String title,
            String body
    ) {
    }

    public record UpdateReq(
            Long id,
            String title,
            String body
    ) {
    }

    public record DeleteReq(
            Long id
    ) {
    }
}
