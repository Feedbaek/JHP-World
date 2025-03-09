package minskim2.JHP_World.domain.test_case.dto;

import lombok.NonNull;

public abstract class TestCaseReq {

    public record Create(
            @NonNull
            Long assignmentId,
            String input,
            String output,
            String description
    ) {
    }

    public record Update(
            @NonNull
            Long testCaseId,
            String input,
            String output,
            String description
    ) {
    }
}
