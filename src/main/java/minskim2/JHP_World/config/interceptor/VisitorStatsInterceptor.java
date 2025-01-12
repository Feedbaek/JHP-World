package minskim2.JHP_World.config.interceptor;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.visitor_log.service.VisitorCountService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class VisitorStatsInterceptor implements HandlerInterceptor {

    private final VisitorCountService visitorCountService;


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // ModelAndView가 존재하고 뷰 이름이 설정된 경우에만 처리
        if (modelAndView != null) {
            long totalVisitors = visitorCountService.getTotalVisitors();
            long todayVisitors = visitorCountService.getTodayVisitors();

            modelAndView.addObject("totalVisitors", totalVisitors);
            modelAndView.addObject("todayVisitors", todayVisitors);
        }
    }
}
