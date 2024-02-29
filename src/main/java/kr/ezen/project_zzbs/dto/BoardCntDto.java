package kr.ezen.project_zzbs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardCntDto {

    private Long totalNoticeCnt;
    private Long totalBoardCnt;
    private Long totalNoobCnt;
    private Long totalFreeCnt;
//    private Long totalGoldCnt;
}
