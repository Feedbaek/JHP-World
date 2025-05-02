package minskim2.JHP_World.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import minskim2.JHP_World.config.login.oauth2.KakaoUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import java.util.List;

@Configuration
public class RedisConfig {

    /**
     * RedisTemplate 설정
     * */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    /**
     * KakaoUser Mixin 설정
     * 역직렬화 시 허용된 클래스만 가능하도록 하기 때문에
     * */
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.CLASS,
            include = JsonTypeInfo.As.PROPERTY,
            property = "@class"
    )
    public static abstract class KakaoUserMixin {
    }

    /**
     * Spring Session Serializer 설정
     * */
    @Bean
    public RedisSerializer<Object> springSessionRedisSerializer() {

        ClassLoader loader = this.getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.addMixIn(KakaoUser.class, KakaoUserMixin.class);
        objectMapper.addMixIn(Long.class, KakaoUserMixin.class);
        objectMapper.addMixIn(List.of("ImmutableCollections$List12").getClass(), KakaoUserMixin.class);

        objectMapper.registerModules(SecurityJackson2Modules.getModules(loader));

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

}
