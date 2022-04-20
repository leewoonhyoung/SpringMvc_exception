package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//이 페이지는 스프링 부트에서 제공하는 BasicErrorController를 해결하기 위한 예제 이다.
@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
        if (ex instanceof IllegalArgumentException) {
            log.info("IllegalArgumentException resolver to 400");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

            //ModelandView를 반환하는 이유는 Exception을 처리하고 정상 흐름처럼 변경하는 것이 목적이다!
            return new ModelAndView();
            }

        }catch (IOException e) {
            log.info("resolver ex", e);
        }
        return null;

    }
}
