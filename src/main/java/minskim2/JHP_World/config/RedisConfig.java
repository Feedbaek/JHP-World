package minskim2.JHP_World.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.List;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {

    /**
     * RedisTemplate 설정
     * 필요하면 주석 해제
     * */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        return redisTemplate;
//    }

    /**
     * KakaoUser Mixin 설정
     * 역직렬화 시 허용된 클래스만 가능하도록 하기 때문에
     * */
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.CLASS,
            include = JsonTypeInfo.As.PROPERTY,
            property = "@class"
    )
    public abstract class KakaoUserMixin {
    }

    /**
     * Spring Session Serializer 설정
     * */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(ObjectMapper objectMapper) {

        ClassLoader loader = this.getClass().getClassLoader();
//
        objectMapper.addMixIn(KakaoUser.class, KakaoUserMixin.class);
        objectMapper.addMixIn(Long.class, KakaoUserMixin.class);
        objectMapper.addMixIn(UserPrincipal.class, KakaoUserMixin.class);
        objectMapper.addMixIn(List.of("dummy").getClass(), KakaoUserMixin.class);

        SecurityJackson2Modules.enableDefaultTyping(objectMapper);

        objectMapper.registerModules(SecurityJackson2Modules.getModules(loader));
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }


//    @Bean
//    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
//                                                           RegisteredClientRepository registeredClientRepository) {
//        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
//        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
//        objectMapper.registerModules(securityModules);
//        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
//        // You will need to write the Mixin for your class so Jackson can marshall it.
//        objectMapper.addMixIn(KakaoUser.class, KakaoUserMixin.class);
//        rowMapper.setObjectMapper(objectMapper);
//        service.setAuthorizationRowMapper(rowMapper);
//        return service;
//    }
}
