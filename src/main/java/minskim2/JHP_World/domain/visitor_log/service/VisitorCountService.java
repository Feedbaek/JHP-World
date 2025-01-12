package minskim2.JHP_World.domain.visitor_log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.domain.visitor_log.repository.VisitorLogRepository;
import minskim2.JHP_World.domain.visitor_log.repository.VisitorQueryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j(topic = "VISITOR_LOG")
public class VisitorCountService {

    private final VisitorQueryRepository visitorQueryRepository;


    // 총 방문자 수를 조회
    @Cacheable(value = "totalVisitorsCache", key = "'totalVisitors'")
    public long getTotalVisitors() {
        return visitorQueryRepository.countTotalVisitors();
    }

    // 오늘 방문자 수를 조회
    @Cacheable(value = "todayVisitorsCache", key = "'todayVisitors'")
    public long getTodayVisitors() {
        return visitorQueryRepository.countTodayVisitors();
    }

    // 총 방문자 수를 갱신
    @CachePut(value = "totalVisitorsCache", key = "'totalVisitors'")
    public long updateTotalVisitors() {
        return visitorQueryRepository.countTotalVisitors();
    }

    // 오늘 방문자 수를 갱신
    @CachePut(value = "todayVisitorsCache", key = "'todayVisitors'")
    public long updateTodayVisitors() {
        return visitorQueryRepository.countTodayVisitors();
    }

    // 오늘 방문자 수 캐시 삭제
    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "todayVisitorsCache", key = "'todayVisitors'")
    public void clearTodayVisitorsCache() {
    }
}
