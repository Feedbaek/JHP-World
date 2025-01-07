package minskim2.JHP_World.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
@EnableJpaRepositories(basePackages = "minskim2.JHP_World.domain.**.repository") // JPA Repository 활성화
public class JpaConfig {

    /**
     * QueryDSL 사용을 위한 JPAQueryFactory 빈 등록
     * */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
