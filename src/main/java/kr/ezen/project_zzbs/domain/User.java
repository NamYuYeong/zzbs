package kr.ezen.project_zzbs.domain;


import kr.ezen.project_zzbs.domain.enumClass.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;     // 로그인할 때 사용하는 아이디
    private String password;    // 비밀번호
    private String nickname;    // 닉네임
    private LocalDateTime createdAt;    // 가입 시간
    private Integer receivedLikeCnt; // 유저가 받은 좋아요 개수 (본인 제외)

    @Enumerated(EnumType.STRING)
    private UserRole userRole;      // 권한

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boards;     // 작성글

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Like> likes;       // 유저가 누른 좋아요

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments; // 댓글

    public void rankUp(UserRole userRole, Authentication auth) {
        this.userRole = userRole;

//        // 현재 저장되어 있는 Authentication 수정 => 재로그인 하지 않아도 권한 수정 되기 위함
//        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
//        updatedAuthorities.add(new SimpleGrantedAuthority(userRole.name()));
//        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
//        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public void likeChange(Integer receivedLikeCnt) {
        this.receivedLikeCnt = receivedLikeCnt;
        if (this.receivedLikeCnt >= 10 && this.userRole.equals(UserRole.NOOB)) {
            this.userRole = UserRole.Genius;
        }
    }

    public void edit(String newPassword, String newNickname) {
        this.password = newPassword;
        this.nickname = newNickname;
    }

    public void changeRole() {
        if (userRole.equals(UserRole.NOOB)) userRole = UserRole.Genius;
        else if (userRole.equals(UserRole.Genius)) userRole = UserRole.BLACKLIST;
        else if (userRole.equals(UserRole.BLACKLIST)) userRole = UserRole.NOOB;
    }
}