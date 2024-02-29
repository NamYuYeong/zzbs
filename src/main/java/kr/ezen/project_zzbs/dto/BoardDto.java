package kr.ezen.project_zzbs.dto;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.UploadImage;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {

    private Long id;
    private String userLoginId;
    private String userNickname;
    private String rtitle;
    private Integer rating;
    private String title;
    private String body;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private MultipartFile newImage;
    private UploadImage uploadImage;
    private String uploadImageName;
    private BigInteger upload_image_id;

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .userLoginId(board.getUser().getLoginId())
                .userNickname(board.getUser().getNickname())
                .rtitle(board.getRtitle())
                .rating(Integer.valueOf(board.getRating()))
                .title(board.getTitle())
                .body(board.getBody())
                .createdAt(board.getCreatedAt())
                .lastModifiedAt(board.getLastModifiedAt())
                .likeCnt(board.getLikes().size())
                .uploadImage(board.getUploadImage())
                .uploadImageName(board.getUploadImage() != null ? board.getUploadImage().getSavedFilename() : null)
                .build();
    }


/*    public static BoardDto from(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .body(board.getBody())
                .uploadImageName(String.valueOf(board.getUploadImage() != null ? board.getUploadImage().getId() : null))
                .build();
    }*/
}