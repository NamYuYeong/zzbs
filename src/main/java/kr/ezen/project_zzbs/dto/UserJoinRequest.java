package kr.ezen.project_zzbs.dto;

import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.enumClass.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserJoinRequest {

    private String loginId;
    private String password;
    private String passwordCheck;
    private String nickname;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .userRole(UserRole.NOOB)
                .createdAt(LocalDateTime.now())
                .receivedLikeCnt(0)
                .build();
    }


}
