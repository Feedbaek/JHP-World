package minskim2.JHP_World.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

    private String token;
    private String repository;
    private String branch;

    private String id;
    private String password;
}
