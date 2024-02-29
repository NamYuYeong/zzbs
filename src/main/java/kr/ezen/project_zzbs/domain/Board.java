package kr.ezen.project_zzbs.domain;


import kr.ezen.project_zzbs.domain.enumClass.BoardCategory;
import kr.ezen.project_zzbs.dto.BoardDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/*
* User가 삭제(탈퇴)되면 유저의 Board, Comment, Like 모두 삭제 => orphanrRemoval=true 설정
UserRole에는 @Enumerated(EnumType.STRING)을 설정 => DB에 저장될 때, "user", "Genius"와 같이 String 타입으로 저장됨
User Entity에는 rankUp(), likeChange(), edit(), changeRole() 4개의 메소드가 존재
rankUp() : user -> Genius 승급할 때 사용
likeChange() : 이 유저의 글에 좋아요가 추가되거나, 취소된 경우 유저의 receivedLikeCnt를 계산하여 수정할 때 사용
edit() : 유저가 수정할 수 있는 nickname, password를 수정할 때 사용
changeRole() : rankUp()과는 달리 관리자가 이 유저의 등급을 수정할 때 사용, user -> Genius -> BLACKLIST -> user 순으로 변경
* */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rtitle; // 가게이름
    private String rating; // 가게별점
    private String title;   // 제목
    private String body;    // 본문

    @Enumerated(EnumType.STRING)
    private BoardCategory category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;      // 작성자

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes;       // 좋아요
    private Integer likeCnt;        // 좋아요 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments; // 댓글
    private Integer commentCnt;     // 댓글 수

    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    private UploadImage uploadImage;

    public void update(BoardDto dto) {
        this.title = dto.getTitle();
        this.body = dto.getBody();
    }

    public void likeChange(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public void commentChange(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

}