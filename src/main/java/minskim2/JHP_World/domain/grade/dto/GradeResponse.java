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

    private Long id;
    private Boolean success;
    private String result;
    private String createdDate;

    public static GradeResponse from(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .success(grade.getSuccess())
                .result(grade.getResult())
                .createdDate(grade.getCreatedDate().toString())
                .build();
    }
}
