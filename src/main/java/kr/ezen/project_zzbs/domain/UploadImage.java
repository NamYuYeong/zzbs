package kr.ezen.project_zzbs.domain;


/*
* User가 삭제(탈퇴)되면 유저의 Board, Comment, Like 모두 삭제 => orphanrRemoval=true 설정
UserRole에는 @Enumerated(EnumType.STRING)을 설정 => DB에 저장될 때, "user", "Genius"와 같이 String 타입으로 저장됨
User Entity에는 rankUp(), likeChange(), edit(), changeRole() 4개의 메소드가 존재
rankUp() : user -> Genius 승급할 때 사용
likeChange() : 이 유저의 글에 좋아요가 추가되거나, 취소된 경우 유저의 receivedLikeCnt를 계산하여 수정할 때 사용
edit() : 유저가 수정할 수 있는 nickname, password를 수정할 때 사용
changeRole() : rankUp()과는 달리 관리자가 이 유저의 등급을 수정할 때 사용, user -> Genius -> BLACKLIST -> user 순으로 변경
* */

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UploadImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;    // 원본 파일명
    private String savedFilename;        // 서버에 저장된 파일명

    @OneToOne(mappedBy = "uploadImage", fetch = FetchType.EAGER)
    private Board board;

}