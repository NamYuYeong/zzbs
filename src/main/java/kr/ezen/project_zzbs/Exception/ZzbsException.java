package kr.ezen.project_zzbs.Exception;

public class ZzbsException extends RuntimeException {

    // 생성자 : 예외 메세지를 매개변수로 받는 생성자
    public ZzbsException(String message) {
        super(message);
    }
}
