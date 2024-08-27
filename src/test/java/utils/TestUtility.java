package utils;

import minskim2.JHP_World.config.login.oauth2.KakaoUser;

public class TestUtility {
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
}
