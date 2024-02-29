package kr.ezen.project_zzbs.controller.acvice;

import kr.ezen.project_zzbs.dto.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect // Aspect = Advice + Pointcut(부가기능을 어느 메서드에 적용할 거냐하는 관점)
public class ValidationCheck {

    @Around("execution(* kr.ezen.project_zzbs..controller.*Controller.*(..))")
//    public interface ProceedingJoinPoint extends JoinPoint
    // ProceedingJoinPoint는 실행중인 메서드에 대한 정보를 제공
    public Object validationCheck(ProceedingJoinPoint jp) throws Throwable{

        // 지금 실행중인 메서드의 매개변수를 얻어오기
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                // ---------- 공통코드 -------------
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        // 어떤 필드에 어떤 문제가 있는지 해당메시지를 맵에 저장
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
                // ---------------------
            }
        }

        return jp.proceed();
    }
}
