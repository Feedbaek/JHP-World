package minskim2.JHP_World.domain.grade.dto;

import lombok.Data;
import minskim2.JHP_World.global.dto.Request;

@Data
public class GradeRequest implements Request {
    private Long solutionId;
    private Long testCaseId;
    private String code;
}
