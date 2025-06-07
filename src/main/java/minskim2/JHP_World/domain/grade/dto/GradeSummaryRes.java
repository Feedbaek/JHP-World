package minskim2.JHP_World.domain.grade.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import minskim2.JHP_World.domain.grade.entity.Grade;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GradeSummaryRes {
    private Long memberId;
    private String memberName;
    private Boolean success;
    private String createdDate;

    public static GradeSummaryRes from(Grade grade) {
        return GradeSummaryRes.builder()
                .memberId(grade.getSolution().getMember().getId())
                .memberName(grade.getSolution().getMember().getName())
                .success(grade.getSuccess())
                .createdDate(grade.getCreatedDate().toString())
                .build();
    }
}
