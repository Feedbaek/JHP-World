package minskim2.JHP_World.domain.test_case.dto;

import minskim2.JHP_World.domain.test_case.entity.TestCase;

public abstract class TestCaseReq {

    public record Get(
            Long id,
            Long assignmentId,
            Long memberId,
            String input,
            String output,
            String description
    ) {
        public static Get of(TestCase testCase) {
            return new Get(
                    testCase.getId(),
                    testCase.getAssignment().getId(),
                    testCase.getMember().getId(),
                    testCase.getInput(),
                    testCase.getOutput(),
                    testCase.getDescription()
            );
        }
    }
}
