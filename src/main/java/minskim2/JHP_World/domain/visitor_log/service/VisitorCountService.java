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

    private final VisitorLogRepository visitorLogRepository;
    private final VisitorQueryRepository visitorQueryRepository;


    @Cacheable(value = "totalVisitorsCache", key = "'totalVisitors'")
    public long getTotalVisitors() {
        // 총 방문자 수를 조회
        var r = visitorQueryRepository.countTotalVisitors();
        log.info("totalVisitors: {}", r);
        return r;
    }

    @Cacheable(value = "todayVisitorsCache", key = "'todayVisitors'")
    public long getTodayVisitors() {
        // 오늘 방문자 수를 조회
        var r = visitorLogRepository.countTodayVisitors();
        log.info("todayVisitors: {}", r);
        return r;
    }

    @CachePut(value = "totalVisitorsCache", key = "'totalVisitors'")
    public long updateTotalVisitors() {
        return visitorQueryRepository.countTotalVisitors();
    }

    @CachePut(value = "todayVisitorsCache", key = "'todayVisitors'")
    public long updateTodayVisitors() {
        return visitorLogRepository.countTodayVisitors();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "todayVisitorsCache", key = "'todayVisitors'")
    public void clearTodayVisitorsCache() {
        // 오늘 방문자 수 캐시 삭제
    }
}
