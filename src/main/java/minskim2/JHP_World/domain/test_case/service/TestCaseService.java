package minskim2.JHP_World.domain.test_case.service;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.login.oauth2.CustomOAuth2User;
import minskim2.JHP_World.domain.assignment.entity.Assignment;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.test_case.dto.TestCaseReq;
import minskim2.JHP_World.domain.test_case.dto.TestCaseRes;
import minskim2.JHP_World.domain.test_case.entity.TestCase;
import minskim2.JHP_World.domain.test_case.repository.TestCaseRepository;
import minskim2.JHP_World.global.exception.CustomException;
import minskim2.JHP_World.global.utils.AuthenticationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static minskim2.JHP_World.global.enums.SizeEnum.TEST_CASE_LIST;
import static minskim2.JHP_World.global.exception.ErrorCode.TEST_CASE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final AuthenticationUtil authenticationUtil;

//    public TestCaseRes.Get findById(Long id) {
//        return TestCaseRes.Get.of(testCaseRepository.findById(id).orElseThrow(
//                () -> CustomException.of(TEST_CASE_NOT_FOUND)
//        ));
//    }

    public TestCaseRes.Get findById(Long id) {

        var result = TestCaseRes.Get.of(testCaseRepository.findById(id)
                .orElseThrow(() -> CustomException.of(TEST_CASE_NOT_FOUND)));
        // 테스트 케이스 권한 확인
        checkTestCaseAuthority(result);

        return result;
    }

    private void checkTestCaseAuthority(TestCaseRes.Get result) {
        try {
            // 테스트 케이스가 비공개 상태라면
            if (!result.isPublic()) {
                // 로그인 상태 확인
                authenticationUtil.checkLogin();
                CustomOAuth2User member = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication();
                // 해당 테스트 케이스를 생성한 사용자가 아닌 경우
                if (!member.getMemberId().equals(result.memberId())) {
                    // 관리자 권한 확인
                    authenticationUtil.checkAdmin();
                }
            }
        } catch (AccessDeniedException ex) {
            // 비공개 상태이며 테스트 케이스에 대한 권한이 없는 경우
            throw CustomException.of(TEST_CASE_NOT_FOUND);
        }
    }

    // TODO: 권한이 있는 테스트 케이스만 조회하도록 수정
    public Page<TestCaseRes.Get> findAllByAssignmentId(Long assignmentId, int page) {
        return testCaseRepository.findAllByAssignmentId(assignmentId, Pageable.ofSize(TEST_CASE_LIST.getSize()).withPage(page))
                .map(TestCaseRes.Get::of);
    }

    /**
     * 테스트 케이스 생성
     * */
    @Transactional
    public TestCaseRes.Get createTestCase(Long memberId, TestCaseReq.Create req) {
        TestCase testCase = TestCase.builder()
                .assignment(Assignment.ById(req.assignmentId()))
                .member(Member.ById(memberId))
                .input(req.input())
                .output(req.output())
                .description(req.description())
                .build();

        testCaseRepository.save(testCase);

        return TestCaseRes.Get.of(testCase);
    }

    @Transactional
    public TestCaseRes.Get createTestCase(Long AdminId, TestCaseReq.CreateByAdmin req) {
        TestCase testCase = TestCase.builder()
                .assignment(Assignment.ById(req.assignmentId()))
                .member(Member.ById(AdminId))
                .input(req.input())
                .output(req.output())
                .description(req.description())
                .build();

        testCaseRepository.save(testCase);

        return TestCaseRes.Get.of(testCase);
    }

    @Transactional
    public TestCaseRes.Get updateTestCase(Long memberId, TestCaseReq.Update req) {
        TestCase testCase = testCaseRepository.findById(req.testCaseId()).orElseThrow(
                () -> CustomException.of(TEST_CASE_NOT_FOUND)
        );

        if (!testCase.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 테스트 케이스에 대한 권한이 없습니다.");
        }

        testCase.update(req);

        return TestCaseRes.Get.of(testCase);
    }

    @Transactional
    public TestCaseRes.Get updateTestCase(Long memberId, TestCaseReq.UpdateByAdmin req) {
        TestCase testCase = testCaseRepository.findById(req.testCaseId()).orElseThrow(
                () -> CustomException.of(TEST_CASE_NOT_FOUND)
        );

        if (!testCase.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 테스트 케이스에 대한 권한이 없습니다.");
        }

        testCase.update(req);

        return TestCaseRes.Get.of(testCase);
    }

    @Transactional
    public void deleteTestCase(Long memberId, Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow(
                () -> CustomException.of(TEST_CASE_NOT_FOUND)
        );

        if (!testCase.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 테스트 케이스에 대한 권한이 없습니다.");
        }

        testCaseRepository.delete(testCase);
    }
}
