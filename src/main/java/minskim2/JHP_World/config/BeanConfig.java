package minskim2.JHP_World.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BeanConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
