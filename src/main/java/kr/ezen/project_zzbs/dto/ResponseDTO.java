package kr.ezen.project_zzbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 인자로 하는 인자생성자
public class ResponseDTO<T> {
    // 응답 상태코드
    private int status;

    // 실제 응답 코드
    private T data;
}
