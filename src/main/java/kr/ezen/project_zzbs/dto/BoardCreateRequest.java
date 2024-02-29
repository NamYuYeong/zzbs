package kr.ezen.project_zzbs.dto;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.enumClass.BoardCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardCreateRequest {

    private String rtitle;
    private Integer rating;
    private String title;
    private String body;
    private MultipartFile uploadImage;

    public Board toEntity(BoardCategory category, User user) {
        return Board.builder()
                .user(user)
                .category(category)
                .rtitle(rtitle)
                .rating(String.valueOf(rating))
                .title(title)
                .body(body)
                .likeCnt(0)
                .commentCnt(0)
                .build();
    }
}
