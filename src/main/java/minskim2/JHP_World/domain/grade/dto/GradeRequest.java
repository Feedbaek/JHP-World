package minskim2.JHP_World.domain.grade.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class GradeRequest {

    @NotBlank
    private Long solutionId;
    @NotBlank
    private Long testCaseId;

    public record Test(
            @NonNull
            Long assignmentId,
            @NonNull
            Long testCaseId,
            @NotBlank
            String code
    ) {
    }
}
