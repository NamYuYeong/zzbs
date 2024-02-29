package kr.ezen.project_zzbs.service;


import kr.ezen.project_zzbs.domain.*;
import kr.ezen.project_zzbs.domain.enumClass.BoardCategory;
import kr.ezen.project_zzbs.domain.enumClass.UserRole;
import kr.ezen.project_zzbs.dto.BoardCntDto;
import kr.ezen.project_zzbs.dto.BoardCreateRequest;
import kr.ezen.project_zzbs.dto.BoardDto;
import kr.ezen.project_zzbs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

//    private final BoardRepository boardRepository;
    @Autowired
    private BoardRepository boardRepository;

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UploadImageService uploadImageService;

    public Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword) {
        if (searchType != null && keyword != null) {
            if (searchType.equals("rtitle")) {
                return boardRepository.findAllByCategoryAndRtitleContainsAndUserUserRoleNot(category, keyword, UserRole.ADMIN, pageRequest);
            } else if (searchType.equals("title")) {
                return boardRepository.findAllByCategoryAndTitleContainsAndUserUserRoleNot(category, keyword, UserRole.ADMIN, pageRequest);
            } else {
                return boardRepository.findAllByCategoryAndUserNicknameContainsAndUserUserRoleNot(category, keyword, UserRole.ADMIN, pageRequest);

            }
        }
        return boardRepository.findAllByCategoryAndUserUserRoleNot(category, UserRole.ADMIN, pageRequest);
    }

    public List<Board> getNotice(BoardCategory category) {
        return boardRepository.findAllByCategoryAndUserUserRole(category, UserRole.ADMIN);
    }

    public BoardDto getBoard(Long boardId, String category) {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        return BoardDto.of(optBoard.get());
    }

    @Transactional
    public Long writeBoard(BoardCreateRequest req, BoardCategory category, String loginId, Authentication auth) throws IOException {
        User loginUser = userRepository.findByLoginId(loginId).get();

        Board savedBoard = boardRepository.save(req.toEntity(category, loginUser));

        UploadImage uploadImage = uploadImageService.saveImage(req.getUploadImage(), savedBoard);
        if (uploadImage != null) {
            savedBoard.setUploadImage(uploadImage);
        }

//        if (category.equals(BoardCategory.NOOB)) {
//            loginUser.rankUp(UserRole.Genius, auth);
//        }
        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(Long boardId, String category, BoardDto dto) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();
        // 게시글에 이미지가 있었으면 삭제
        if (board.getUploadImage() != null) {
            uploadImageService.deleteImage(board.getUploadImage());
            board.setUploadImage(null);
        }

        UploadImage uploadImage = uploadImageService.saveImage(dto.getNewImage(), board);
        if (uploadImage != null) {
            board.setUploadImage(uploadImage);
        }
        board.update(dto);

        return board.getId();
    }
@Transactional
    public Long deleteBoard(Long boardId, String category) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 null return
        if (optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        User boardUser = optBoard.get().getUser();
        boardUser.likeChange(boardUser.getReceivedLikeCnt() - optBoard.get().getLikeCnt());
        if (optBoard.get().getUser() != null) {
            uploadImageService.deleteImage(optBoard.get().getUploadImage());
        }
        boardRepository.deleteById(boardId);
        return boardId;
    }

    public String getCategory(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board.getCategory().toString().toLowerCase();
    }

    public List<Board> findMyBoard(String category, String loginId) {
        if (category.equals("board")) {
            return boardRepository.findAllByUserLoginId(loginId);
        } else if (category.equals("like")) {
            List<Like> likes = likeRepository.findAllByUserLoginId(loginId);
            List<Board> boards = new ArrayList<>();
            for (Like like : likes) {
                boards.add(like.getBoard());
            }
            return boards;
        } else if (category.equals("comment")) {
            List<Comment> comments = commentRepository.findAllByUserLoginId(loginId);
            List<Board> boards = new ArrayList<>();
            HashSet<Long> commentIds = new HashSet<>();

            for (Comment comment : comments) {
                if (!commentIds.contains(comment.getBoard().getId())) {
                    boards.add(comment.getBoard());
                    commentIds.add(comment.getBoard().getId());
                }
            }
            return boards;
        }
        return null;
    }

    public BoardCntDto getBoardCnt(){
        return BoardCntDto.builder()
                .totalBoardCnt(boardRepository.count())
                .totalNoticeCnt(boardRepository.countAllByUserUserRole(UserRole.ADMIN))
                .totalNoobCnt(boardRepository.countAllByCategoryAndUserUserRoleNot(BoardCategory.NOOB, UserRole.ADMIN))
                .totalFreeCnt(boardRepository.countAllByCategoryAndUserUserRoleNot(BoardCategory.FREE, UserRole.ADMIN))
                .build();
    }
@Transactional
    /* 레이팅*/
    public Double getAverageRatingByPlaceName(String place_name) {
        return boardRepository.findAverageRatingByPlaceName(place_name);
    }
@Transactional
    /* zzbs 리뷰 */
//    public List<BoardInterface> getRandomRatingFiveBoard(String place_name) {
//        return boardRepository.findRandomRatingFiveBoard(place_name);
//    }
    public List<BoardInterface> getRandomRatingFiveBoard(String place_name) {
        return boardRepository.findRandomRatingFiveBoard(place_name);
    }

    public List<String> getHeaderImages() {
        List<Board> boards = boardRepository.findByRating("5");
        List<String> imageUrls = new ArrayList<>();
        for (Board board : boards) {
            if (board.getUploadImage() != null) {
                imageUrls.add(board.getRtitle()+"|"+"/boards/images/" + board.getUploadImage().getSavedFilename()+"|"+"/boards/free/"+board.getId());
            }
        }
        return imageUrls;
    }
}