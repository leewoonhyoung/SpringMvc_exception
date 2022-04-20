package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "잘못된 요청 오류")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") //reason="error.bad" 는 mesasges.properties에 메세지화 해놓은것을 불러온다.
public class BadRequestException extends RuntimeException {

}
