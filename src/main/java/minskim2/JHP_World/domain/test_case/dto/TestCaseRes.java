package minskim2.JHP_World.domain.test_case.dto;

import minskim2.JHP_World.domain.test_case.entity.TestCase;

public abstract class TestCaseRes {

    public record Get(
            Long id,
            Long assignmentId,
            String assignmentTitle,
            Long memberId,
            String member,
            String input,
            String output,
            String description,
            Boolean isPublic
    ) {
        public static Get of(TestCase testCase) {
            return new Get(
                    testCase.getId(),
                    testCase.getAssignment().getId(),
                    testCase.getAssignment().getTitle(),
                    testCase.getMember().getId(),
                    testCase.getMember().getName(),
                    testCase.getInput(),
                    testCase.getOutput(),
                    testCase.getDescription(),
                    testCase.getIsPublic()
            );
        }
    }
}
