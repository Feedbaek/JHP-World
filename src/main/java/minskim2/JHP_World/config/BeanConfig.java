package minskim2.JHP_World.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
