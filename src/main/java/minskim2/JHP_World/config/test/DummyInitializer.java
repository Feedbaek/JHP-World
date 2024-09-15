
package minskim2.JHP_World.config.test;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.EnvBean;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.assignment.repository.AssignmentRepository;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.lecture.repository.LectureRepository;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.entity.Role;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import minskim2.JHP_World.domain.member.repository.RoleRepository;
import minskim2.JHP_World.domain.post.entity.Post;
import minskim2.JHP_World.domain.post.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** 테스트용 더미 데이터 생성 */
@Component
@RequiredArgsConstructor
public class DummyInitializer implements CommandLineRunner {
    private final EnvBean envBean;
    private final LectureRepository lectureRepository;
    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;

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
                    .title("과제 제목 - " + i)
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
                    .title("과제 제목 - " + i)
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
                    .title("과제 제목 - " + i)
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
                    .title("과제 제목 - " + i)
                    .body("과제 내용 - " + i)
                    .lecture(lecture)
                    .build();
            assignmentRepository.save(assignment);
        }
    }

    /**
     * 더미 Post 데이터 생성
     * */
    private void initPost() {
        Member member = memberRepository.findById(1L).orElseGet(() -> {
            Role userRole = roleRepository.findByName(RoleName.USER).orElseThrow();
            Member member1 = Member.builder()
                    .id(1L)
                    .name("test")
                    .oauth2id("kakao:testOauth2id")
                    .role(userRole)
                    .isEnabled(true)
                    .build();
            return memberRepository.save(member1);
        });
        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("문제해결기법").orElseThrow();
            if (postRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Post post = Post.builder()
                    .member(member)
                    .title("포스트 제목 - " + i)
                    .body("포스트 내용 - " + i)
                    .lecture(lecture)
                    .build();
            postRepository.save(post);
        }
        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("자료구조").orElseThrow();
            if (postRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Post post = Post.builder()
                    .member(member)
                    .title("포스트 제목 - " + i)
                    .body("포스트 내용 - " + i)
                    .lecture(lecture)
                    .build();
            postRepository.save(post);
        }
        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("알고리즘설계").orElseThrow();
            if (postRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Post post = Post.builder()
                    .member(member)
                    .title("포스트 제목 - " + i)
                    .body("포스트 내용 - " + i)
                    .lecture(lecture)
                    .build();
            postRepository.save(post);
        }
        for (int i=1; i<=3; ++i) {
            Lecture lecture = lectureRepository.findByName("오토마타").orElseThrow();
            if (postRepository.countByLectureId(lecture.getId()) >= 3) {
                continue;
            }
            Post post = Post.builder()
                    .member(member)
                    .title("포스트 제목 - " + i)
                    .body("포스트 내용 - " + i)
                    .lecture(lecture)
                    .build();
            postRepository.save(post);
        }
    }

    /**
     * 더미 데이터 생성 메소드
     * */
    @Transactional
    @Override
    public void run(String... args) {
        if (envBean.getTestEnable().equals("true")) {
            initLecture();
            initAssignment();
            initPost();
        }
    }
}

