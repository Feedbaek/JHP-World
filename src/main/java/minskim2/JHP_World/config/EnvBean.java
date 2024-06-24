package minskim2.JHP_World.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 환경 변수를 application.yml 에서 가져오거나
 * 스프링 빈을 등록하기 위한 객체
 **/
@Getter
@Component
public class EnvBean {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    @Value("${jwt.access-token-time}")
    private long accessTokenTime;
    @Value("${jwt.refresh-token-time}")
    private long refreshTokenTime;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
}
