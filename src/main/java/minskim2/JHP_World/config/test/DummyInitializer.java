
package minskim2.JHP_World.config.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** 테스트용 더미 데이터 생성 */
@Slf4j(topic = "DummyInitializer")
@Component
@RequiredArgsConstructor
public class DummyInitializer implements CommandLineRunner {

    private final EnvBean envBean;
    private final LectureRepository lectureRepository;
    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;
    private final TestCaseRepository testCaseRepository;

    /**
    * 강의 데이터 생성
    * */
    private void initLecture() {
        // 문제해결기법 강의
        if (lectureRepository.findByName("문제해결기법").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .id(1L)
                    .name("문제해결기법")
                    .build();
            lectureRepository.save(lecture);
        }
        // 자료구조 강의
        if (lectureRepository.findByName("자료구조").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .id(2L)
                    .name("자료구조")
                    .build();
            lectureRepository.save(lecture);
        }
        // 알고리즘설계 강의
        if (lectureRepository.findByName("알고리즘설계").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .id(3L)
                    .name("알고리즘설계")
                    .build();
            lectureRepository.save(lecture);
        }
        // 오토마타 강의
        if (lectureRepository.findByName("오토마타").isEmpty()) {
            Lecture lecture = Lecture.builder()
                    .id(4L)
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
     * 더미 Admin, Post 데이터 생성
     * */
    private void initPost() {
        Member member = memberRepository.findByName("admin").orElseThrow();
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
     * 더미 테스트 케이스 생성
     * */
    private void initTestCase() {

        long cnt = testCaseRepository.count();
        for (long i=cnt; i<=3; ++i) {
            testCaseRepository.findById(i).orElseGet(() -> {
                Member member = memberRepository.findByName("admin").orElseThrow();
                Assignment assignment = assignmentRepository.findByTitle("과제 제목 - 1").getFirst();

                TestCase testCase1 = TestCase.builder()
                        .member(member)
                        .assignment(assignment)
                        .input("1 2")
                        .output("3")
                        .description("1과 2를 더하는 테스트.")
                        .isPublic(true)
                        .build();

                return testCaseRepository.save(testCase1);
            });
        }
    }

    /**
     * 더미 사용자 생성
     * */
    private void initMember() {
        // Admin
        memberRepository.findByName("admin").orElseGet(() -> {
            Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN).orElseThrow();
            Member member1 = Member.builder()
                    .name("admin")
                    .oauth2id("kakao:adminOauth2id")
                    .role(adminRole)
                    .isEnabled(true)
                    .build();
            return memberRepository.save(member1);
        });

        // User
        long cnt = memberRepository.count();
        for (long i=cnt; i<=5; ++i) {
            if (memberRepository.findByName("user" + i).isEmpty()) {
                Role userRole = roleRepository.findByRoleName(RoleName.USER).orElseThrow();
                Member member1 = Member.builder()
                        .name("user" + i)
                        .oauth2id("kakao:user" + i + "Oauth2id")
                        .role(userRole)
                        .isEnabled(true)
                        .build();
                memberRepository.save(member1);
            }
        }
    }

    /**
     * 더미 데이터 생성 메소드
     * */
    @Transactional
    @Override
    public void run(String... args) {
        if (envBean.getTestEnable().equals("true")) {
            initMember();
            initLecture();
            initAssignment();
            initPost();
            initTestCase();
        }
    }
}

