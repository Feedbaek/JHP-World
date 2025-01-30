package minskim2.JHP_World.domain.grade.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import minskim2.JHP_World.domain.grade.entity.Grade;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GradeResponse {

    private Boolean success;
    private String result;

    public static GradeResponse from(Grade grade) {
        return GradeResponse.builder()
                .success(grade.getSuccess())
                .result(grade.getResult())
                .build();
    }
}
