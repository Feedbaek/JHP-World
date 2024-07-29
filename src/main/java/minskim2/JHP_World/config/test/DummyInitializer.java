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
    * 강의 데이터 생성
    * */
    private void initLecture() {
        // 문제해결기법 강의
        if (lectureRepository.findByName("문제해결기법").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .name("문제해결기법")
                    .build();
            lectureRepository.save(lecture);
        }
        // 자료구조 강의
        if (lectureRepository.findByName("자료구조").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .name("자료구조")
                    .build();
            lectureRepository.save(lecture);
        }
        // 알고리즘설계 강의
        if (lectureRepository.findByName("알고리즘설계").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .name("알고리즘설계")
                    .build();
            lectureRepository.save(lecture);
        }
        // 오토마타 강의
        if (lectureRepository.findByName("오토마타").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .name("오토마타")
                    .build();
            lectureRepository.save(lecture);
        }
    }

    /**
     * 더미 과제 데이터 생성
     */
    private void initAssignment() {
        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("문제해결기법").orElseThrow();
            if (assignmentRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }

        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("자료구조").orElseThrow();
            if (assignmentRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }

        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("알고리즘설계").orElseThrow();
            if (assignmentRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Assignment assignment = Assignment.builder()
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }

        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("오토마타").orElseThrow();
            if (assignmentRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
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
