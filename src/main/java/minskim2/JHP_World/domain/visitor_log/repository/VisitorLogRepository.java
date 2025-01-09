package minskim2.JHP_World.domain.visitor_log.repository;

import minskim2.JHP_World.domain.visitor_log.entity.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {
    @Query("SELECT COUNT(DISTINCT v.ip) FROM VisitorLog v WHERE v.createdDate >= CURRENT_DATE")
    long countTodayVisitors();
}
