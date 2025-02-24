package minskim2.JHP_World.config;

import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final EnvBean envBean;

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public GitHub gitHub() throws IOException {
        return new GitHubBuilder().withOAuthToken(envBean.getGithubToken()).build();
    }
}
