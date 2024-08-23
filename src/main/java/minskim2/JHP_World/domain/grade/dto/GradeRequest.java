package minskim2.JHP_World.domain.grade.dto;

import lombok.Data;

@Data
public class GradeRequest {
    private Long solutionId;
    private Long testCaseId;
    private String code;
}
