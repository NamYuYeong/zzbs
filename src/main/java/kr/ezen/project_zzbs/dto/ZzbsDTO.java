package kr.ezen.project_zzbs.dto;

import kr.ezen.project_zzbs.domain.Zzbs;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZzbsDTO {

    private int bid;

    private String nation;

    private String food;

//    private String newNation;

//    private String newFood;

    public static ZzbsDTO of(Zzbs zzbs) {
        return ZzbsDTO.builder()
                .nation(zzbs.getNation())
                .food(zzbs.getFood())
                .build();
    }


}
