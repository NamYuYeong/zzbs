package kr.ezen.project_zzbs.Exception;

import kr.ezen.project_zzbs.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// @ControllerAdvice는 어플리케이션 내에 모든 컨트롤러에서 발생하는 예외를 처리
// @ControllerAdvice를 사용하여 컨트롤러는 좀 더 컨트롤러의 역할에 집중 할 수 있고
// 중복된 예외처리 코드를 제거할 수 있다. 즉, 관심사의 분리를 이뤄낼 수 있다.
@ControllerAdvice
@RestController // == @Controller + @ResponseBody
public class ZzbsExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    public String globalExceptionHandler(Exception e) {
//        return "<h1>" + e.getMessage() + "</h1>";
//    }

    // ResponseDTO를 이용하면 일관된 응답처리를 할 수 있다.
    @ExceptionHandler(value = Exception.class)
    public ResponseDTO<String> globalExceptionHandler(Exception e) {
        return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
