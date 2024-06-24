package minskim2.JHP_World.domain.member.repository;

import minskim2.JHP_World.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauth2id(String oauth2Id);
}
