package kr.ezen.project_zzbs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCntDto {

    private Long totalUserCnt;
    private Long totalAdminCnt;
    private Long totalNoobCnt;
    private Long totalGeniusCnt;
    private Long totalBlacklistCnt;
}
