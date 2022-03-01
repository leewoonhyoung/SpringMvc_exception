package hello.exception.servlet;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 1번째 만약 /error-ex에서 runtimeException이 발생했다면 WebserverCustonmizer로 넘어가라
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외 발생");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException{
        response.sendError(404, "404 오류!");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException{
        response.sendError(500);

    }


}
