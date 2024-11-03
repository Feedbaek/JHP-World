package minskim2.JHP_World.domain.test_case.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import minskim2.JHP_World.domain.test_case.entity.TestCase;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCaseDto {

    private Long id;
    private Long assignmentId;
    private Long memberId;
    private String input;
    private String output;
    private String description;


    public static TestCaseDto from(TestCase testCase) {
        return TestCaseDto.builder()
                .id(testCase.getId())
                .assignmentId(testCase.getAssignment().getId())
                .memberId(testCase.getMember().getId())
                .input(testCase.getInput())
                .output(testCase.getOutput())
                .description(testCase.getDescription())
                .build();
    }
}
