package minskim2.JHP_World.domain.test_case.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class TestCaseQ {
    private Long id;
    private Long assignmentId;
    private String assignmentTitle;
    private Long memberId;
    private String member;
    private String description;
    private Long runCount;
    private Long commentCount;
    private Long recommendCount;
    private LocalDateTime createdDate;
}
