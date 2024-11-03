package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import minskim2.JHP_World.domain.grade.dto.GradeRequest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@Log4j2
public class TestUtility {
    public static final ObjectMapper objectMapper = new ObjectMapper();

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
        gradeRequest.setSolutionId(1L);
        gradeRequest.setTestCaseId(1L);
        gradeRequest.setCode("code");
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
}
