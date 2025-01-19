package minskim2.JHP_World.domain.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssignmentReq {

    public record Create(
            @NotNull
            Long lectureId,
            @NotBlank
            String title,
            @NotBlank
            String body
    ) {
    }

    public record Update(
            Long id,
            String title,
            String body
    ) {
    }

    public record Delete(
            Long id
    ) {
    }
}
