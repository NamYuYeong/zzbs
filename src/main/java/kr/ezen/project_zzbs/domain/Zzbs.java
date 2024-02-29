package kr.ezen.project_zzbs.domain;

import kr.ezen.project_zzbs.dto.UserDto;
import kr.ezen.project_zzbs.dto.ZzbsDTO;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Zzbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    private Long bid;

    @Column(nullable = false, length = 100)
    private String nation;

    @Column(nullable = false, length = 100)
    private String food;


//    @Builder
//    public static ZzbsDTO of(Zzbs zzbs) {
//        return ZzbsDTO.builder()
//                .nation(zzbs.getNation())
//                .food(zzbs.getFood())
//                .build();
//    }

    public void categoryEdit(String newNation, String newFood) {
        this.nation = newNation;
        this.food = newFood;
    }

//    public boolean categoryEdit(boolean equals) {
//    }
}
