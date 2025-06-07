package minskim2.JHP_World.domain.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class AssignmentReq {

    public record Create(
            @NotNull
            Long lectureId,
            @NotBlank
            String title,
            @NotBlank
            String body,
            MultipartFile file
    ) {
    }

    public record Update(
            Long id,
            String title,
            String body
    ) {
    }

    public record UpdateFile(
            Long assignmentId,
            MultipartFile file
    ) {
    }

    public record Delete(
            Long id
    ) {
    }
}
