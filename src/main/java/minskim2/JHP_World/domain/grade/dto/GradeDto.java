package minskim2.JHP_World.domain.grade.dto;

import lombok.Builder;
import minskim2.JHP_World.domain.grade.entity.Grade;

@Builder
public record GradeDto(
        Long id,
        Long solutionId,
        Long testCaseId,
        Boolean success,
        String result
) {
    public static GradeDto from(Grade save) {
        return GradeDto.builder()
                .id(save.getId())
                .solutionId(save.getSolution().getId())
                .testCaseId(save.getTestCase().getId())
                .success(save.getSuccess())
                .result(save.getResult())
                .build();
    }
}
