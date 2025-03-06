package minskim2.JHP_World.domain.test_case.dto;

public abstract class TestCaseReq {

    public record Create(
            Long assignmentId,
            String input,
            String output,
            String description
    ) {
    }

    public record Update(
            Long testCaseId,
            String input,
            String output,
            String description
    ) {
    }
}
