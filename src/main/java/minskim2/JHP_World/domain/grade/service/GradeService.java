package minskim2.JHP_World.domain.grade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.grade.dto.GradeResponse;
import minskim2.JHP_World.domain.grade.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Log4j2(topic = "GradeService")
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    @Transactional
    public GradeResponse testGrade(Long memberId, GradeRequest gradeRequest) {
        // TODO: 입력 값 검증
        // TODO: 과제 테스트 실행로직 추가

        log.info("과제 테스트 실행");

        return GradeResponse.builder()
                .message("테스트 성공")
                .result("PASS")
                .build();
    }
}
