package minskim2.JHP_World.domain.grade.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GradeRequest {

    @NotBlank
    private Long solutionId;
    @NotBlank
    private Long testCaseId;

    public record Test(
            Long testCaseId,
            String code
    ) {
    }
}
