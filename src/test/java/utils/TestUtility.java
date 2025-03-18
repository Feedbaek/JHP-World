package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import minskim2.JHP_World.domain.lecture.entity.Lecture;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.entity.Role;
import minskim2.JHP_World.domain.member.enums.RoleName;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@Log4j2
public class TestUtility {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private static final ThreadLocal<Long> lectureSequence = new ThreadLocal<>();
    private static final ThreadLocal<Long> assignmentSequence = new ThreadLocal<>();
    private static final ThreadLocal<Long> memberSequence = new ThreadLocal<>();
    private static final ThreadLocal<Long> testcaseSequence = new ThreadLocal<>();


    public static void sequenceInit() {
        lectureSequence.set(1L);
        assignmentSequence.set(1L);
        memberSequence.set(1L);
        testcaseSequence.set(1L);
    }

    public static KakaoUser MockKakaoUser() {
        return KakaoUser.builder()
                .memberId(1L)
                .registrationId("kakao")
                .attributes(null)
                .oauth2Id("123456789")
                .name("mockName")
                .authorities(null)
                .build();
    }

    public static GradeRequest makeGradeRequest() {
        GradeRequest gradeRequest = new GradeRequest();
//        gradeRequest.setSolutionId(1L);
//        gradeRequest.setTestCaseId(1L);
//        gradeRequest.setCode("code");
        return gradeRequest;
    }

    public static MockHttpServletRequestBuilder JsonRequestChain(MockHttpServletRequestBuilder actionWithUrl, GradeRequest request) {

        try {
            return actionWithUrl
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
                    .with(oauth2Login().oauth2User(MockKakaoUser()));
        } catch (JsonProcessingException e) {
            log.info(e);
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public static Lecture makeLecture(EntityManager em) {
        Long seq = lectureSequence.get();
        lectureSequence.set(seq + 1);

        Lecture lecture = Lecture.builder()
                .name("lecture-" + seq)
                .build();

        em.persist(lecture);
        return lecture;
    }

    public static Assignment makeAssignment(EntityManager em) {
        Long seq = assignmentSequence.get();
        assignmentSequence.set(seq + 1);

        Lecture lecture = makeLecture(em);

        Assignment assignment = Assignment.builder()
                .title("title-" + seq)
                .body("body-" + seq)
                .lecture(lecture)
                .build();

        em.persist(assignment);
        return assignment;
    }

    public static List<Assignment> makeAssignmentList(EntityManager em, int size) {
        Lecture lecture = makeLecture(em);
        List<Assignment> assignmentList = new ArrayList<>();

        for (int i=0; i<size; ++i) {
            Long seq = assignmentSequence.get();
            assignmentSequence.set(seq + 1);

            Assignment assignment = Assignment.builder()
                    .title("title-" + seq)
                    .body("body-" + seq)
                    .lecture(lecture)
                    .build();

            em.persist(assignment);
            assignmentList.add(assignment);
        }
        return assignmentList;
    }

    public static void makeRole(EntityManager em) {
        Role user = new Role(RoleName.USER);
        Role admin = new Role(RoleName.ADMIN);

        em.persist(user);
        em.persist(admin);
    }

    public static Member makeMember(EntityManager em) {
        Long seq = memberSequence.get();
        memberSequence.set(seq + 1);

        Role role = new Role(RoleName.USER);
        em.persist(role);

        Member member = Member.builder()
                .name("name-" + seq)
                .oauth2id("oauth2id-" + seq)
                .role(role)
                .isEnabled(true)
                .build();

        em.persist(member);
        return member;
    }

    public static TestCase makeTestCase(EntityManager em) {
        Assignment assignment = makeAssignment(em);
        Member member = makeMember(em);

        Long seq = testcaseSequence.get();
        testcaseSequence.set(seq + 1);

        TestCase testCase = TestCase.builder()
                .assignment(assignment)
                .member(member)
                .input("input-" + seq)
                .output("output-" + seq)
                .description("description-" + seq)
                .isPublic(true)
                .build();

        em.persist(testCase);
        return testCase;
    }

    public static List<TestCase> makeTestCaseList(EntityManager em, int size) {
        Assignment assignment = makeAssignment(em);
        Member member = makeMember(em);

        List<TestCase> testCaseList = new ArrayList<>();

        for (int i=0; i<size; ++i) {
            Long seq = testcaseSequence.get();
            testcaseSequence.set(seq + 1);

            TestCase testCase = TestCase.builder()
                    .assignment(assignment)
                    .member(member)
                    .input("input-" + seq)
                    .output("output-" + seq)
                    .description("description-" + seq)
                    .isPublic(true)
                    .build();

            em.persist(testCase);
            testCaseList.add(testCase);
        }
        em.flush();
        return testCaseList;
    }
}
