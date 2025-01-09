package minskim2.JHP_World.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostReq {

    public record CreateReq(
            @NotNull
            Long lectureId,
            @NotBlank
            String title,
            @NotBlank
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
