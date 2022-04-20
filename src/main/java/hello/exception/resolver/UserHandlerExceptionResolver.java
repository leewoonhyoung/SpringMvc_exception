package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//이 페이지는 내가 만든 ExceptionHandler를 통해 ApiExceptionController 해결하기 위한 예제 이다.
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // http header가 json 인지 아닌지에 따라 2가지로 응답함.
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try{
            if (ex instanceof UserException){
                log.info("UserException resolver to 400");

                //request에서 ACCEPT값을 가져와 여기서 ACCEPT 값은 appliation/json 같은 형식의 값이다.
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                //만약  ACCEPT 값이 "application/json값이라면
                if("application/json".equals(acceptHeader)){

                    //HashMap을 만들어서 error값을 담을 errorResult를 만들어.
                    Map<String, Object> errorResult = new HashMap<>();

                    //errorResult에 ex값이랑 message 를 넣어서 error를 보관해라.
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult); //json을 문자로 변경

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);

                    return new ModelAndView(); // 예외는 먹지만 서블릿까지 response가 전달이 된다.

                } else{
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e){
            log.info("resolver ex", e);
        }
    return null;
    }

}
