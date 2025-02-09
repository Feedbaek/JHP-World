package minskim2.JHP_World.domain.assignment.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AssignmentRes {

    @Builder
    public record AdminGet(
            Long id,
            String title,
            String body,
            Long lectureId
            ) {
    }
}
