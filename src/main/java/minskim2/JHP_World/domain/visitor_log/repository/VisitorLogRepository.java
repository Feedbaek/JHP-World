package minskim2.JHP_World.domain.visitor_log.repository;

import minskim2.JHP_World.domain.visitor_log.entity.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {
    // 가장 최근 로그인 세션 ID 하나만 조회
    @Query(value = "SELECT v.session_id FROM visitor_log v WHERE v.member_id = :id ORDER BY v.id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findSessionIdByMemberId(Long id);
}
