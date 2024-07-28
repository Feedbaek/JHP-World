package minskim2.JHP_World.config.test;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.EnvBean;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.assignment.repository.AssignmentRepository;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.repository.LectureRepository;
import org.springframework.stereotype.Component;

/** 테스트용 더미 데이터 생성 */
@Component
@RequiredArgsConstructor
public class DummyInitializer {
    private final EnvBean envBean;
    private final LectureRepository lectureRepository;
    private final AssignmentRepository assignmentRepository;

    /**
    * 더미 강의 데이터 생성
    * */
    private void initLecture() {
        for (int i=1; i<=3; ++i) {
            if (lectureRepository.findById((long) i).isPresent()) {
                continue;
            }
            Lecture lecture = Lecture.builder()
                    .name("강의 이름 - " + i)
                    .build();
            lectureRepository.save(lecture);
        }
    }

    /**
     * 더미 과제 데이터 생성
     */
    private void initAssignment() {
        for (int i=1; i<=3; ++i) {
            if (assignmentRepository.findById((long) i).isPresent()) {
                continue;
            }
            Lecture lecture = lectureRepository.findById(1L).orElse(null);
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }

        for (int i=4; i<=6; ++i) {
            if (assignmentRepository.findById((long) i).isPresent()) {
                continue;
            }
            Lecture lecture = lectureRepository.findById(2L).orElse(null);
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }

        for (int i=7; i<=9; ++i) {
            if (assignmentRepository.findById((long) i).isPresent()) {
                continue;
            }
            Lecture lecture = lectureRepository.findById(3L).orElse(null);
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }
    }

    /**
     * 더미 데이터 생성 메소드
     * */
    @PostConstruct
    public void init() {
        if (envBean.getTestEnable().equals("true")) {
            initLecture();
            initAssignment();
        }
    }
}
