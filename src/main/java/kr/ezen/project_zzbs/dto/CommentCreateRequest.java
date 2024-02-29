package kr.ezen.project_zzbs.dto;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.Comment;
import kr.ezen.project_zzbs.domain.User;
import lombok.Data;

@Data
public class CommentCreateRequest {

    private String body;

    public Comment toEntity(Board board, User user) {
        return Comment.builder()
                .user(user)
                .board(board)
                .body(body)
                .build();
    }
}
