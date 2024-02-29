package kr.ezen.project_zzbs.service;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.Comment;
import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.enumClass.UserRole;
import kr.ezen.project_zzbs.dto.CommentCreateRequest;
import kr.ezen.project_zzbs.repository.BoardRepository;
import kr.ezen.project_zzbs.repository.CommentRepository;
import kr.ezen.project_zzbs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void writeComment(Long boardId, CommentCreateRequest req, String loginId) {
        Board board = boardRepository.findById(boardId).get();
        User user = userRepository.findByLoginId(loginId).get();
        board.commentChange(board.getCommentCnt() + 1);
        commentRepository.save(req.toEntity(board, user));
    }

    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public Long editComment(Long commentId, String newBody, String loginId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        Optional<User> optUser = userRepository.findByLoginId(loginId);
        if (optComment.isEmpty() || optUser.isEmpty() || !optComment.get().getUser().equals(optUser.get())) {
            return null;
        }

        Comment comment = optComment.get();
        comment.update(newBody);

        return comment.getBoard().getId();
    }

    public Long deleteComment(Long commentId, String loginId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        Optional<User> optUser = userRepository.findByLoginId(loginId);
        if (optComment.isEmpty() || optUser.isEmpty() ||
                (!optComment.get().getUser().equals(optUser.get()) && !optUser.get().getUserRole().equals(UserRole.ADMIN))) {
            return null;
        }

        Board board = optComment.get().getBoard();
        board.commentChange(board.getCommentCnt() - 1);

        commentRepository.delete(optComment.get());
        return board.getId();
    }
}
