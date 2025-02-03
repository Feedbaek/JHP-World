package minskim2.JHP_World.config.resolver;

import minskim2.JHP_World.config.anotation.PageParam;
import minskim2.JHP_World.global.exception.CustomException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static minskim2.JHP_World.global.exception.ErrorCode.PAGE_NOT_POSITIVE;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PageParam.class) &&
                parameter.getParameterType().equals(int.class) &&
                Objects.requireNonNull(parameter.getParameterName()).equals("page");
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String pageParam = webRequest.getParameter("page");
        // 기본값 1
        int page = 1;
        // page 파라미터가 있을 경우 파싱
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
            // 기본값이 1보다 작을 경우 예외 처리
            if (page <= 0) {
                throw CustomException.of(PAGE_NOT_POSITIVE);
            }
        }
        // 0부터 시작하는 페이지로 변환
        return page - 1;
    }
}
