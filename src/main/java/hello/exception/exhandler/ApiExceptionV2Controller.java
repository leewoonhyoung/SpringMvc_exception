package hello.exception.exhandler;

import hello.exception.api.ApiExceptionController;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    /**
        ExceptionHandler의 예외 처리는 자식 클래스까지 처리가 가능하다.

     ExceptionHandler란
     Controller에서 예외가 터지면 ExceptionResolver에게 예외 해결을 시도한다.
     ExceptionResolver는 ExceptionHandlerExceptionResolver에게 가장먼저 물어본다.

     ExceptionHandler가 잡아서 (illegalArgumentException 발생시)
     해당 메서드들을 실행한다.
    */

    //ExceptionHandler만 처리하게되면 오류를 다음과 같이 처리해서 정상흐름으로 WAS에게 보내기 때문에 정상코드 200발생,
    //그래서 오류로 인식된 데이터임을 알려주기 위해 ResponseStatus를 통해 예외상태 코드를 달아주는 어노테이션을 사용한다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    //단지 ResponseEntity도 사용 가능하다라는 것을 보여줌.
    // 위의 ErrorResult , ResponseEntity와 비교해서 공부해보자.
    @ExceptionHandler(UserException.class) // 컨트롤러 호출하는것과 매우 흡사.
    public ResponseEntity<ErrorResult> userExHandle(UserException e){
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    //해외 ip 서버 error ExceptinoHandler. ResponseSatus = > 서버 에러

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
