package minskim2.JHP_World.domain.solution.dto;

import lombok.Builder;
import minskim2.JHP_World.domain.solution.entity.Solution;

@Builder
public record SolutionDto(
        Long id,
        String sourceCode,
        Long memberId,
        Long assignmentId
) {

    public static SolutionDto from(Solution solution) {
        return SolutionDto.builder()
                .id(solution.getId())
                .sourceCode(solution.getSourceCode())
                .memberId(solution.getMember().getId())
                .assignmentId(solution.getAssignment().getId())
                .build();
    }
}
