package kr.ezen.project_zzbs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private String place_name;
    private String road_address_name;
    private String address_name;
    private String phone;

}
