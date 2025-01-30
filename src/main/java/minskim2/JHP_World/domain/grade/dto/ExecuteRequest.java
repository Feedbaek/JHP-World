package minskim2.JHP_World.domain.grade.dto;

import lombok.Builder;

public abstract class ExecuteRequest {

    /**
     * 과제 테스트 실행 요청 메시지
     * */
    @Builder
    public record PubMessage(
            Long gradeId,
            String input,
            String output,
            String code
    ) {
    }

    /**
     * 과제 테스트 실행 결과 메시지
     * */
    @Builder
    public record SubMessage(
            Long gradeId,
            Boolean success,
            String result
    ) {
    }
}
