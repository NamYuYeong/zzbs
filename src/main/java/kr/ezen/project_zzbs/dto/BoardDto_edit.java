//package kr.ezen.project_zzbs.dto;
//
//import kr.ezen.project_zzbs.domain.Board;
//import kr.ezen.project_zzbs.domain.UploadImage;
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//
//@Data
//@Builder
//public class BoardDto {
//
//    private Long id;
//    private String userLoginId;
//    private String userNickname;
//    private String rtitle;
//    private Integer rating;
//    private String title;
//    private String body;
//    private Integer likeCnt;
//    private LocalDateTime createdAt;
//    private LocalDateTime lastModifiedAt;
//    private MultipartFile newImage;
//    private Long uploadImageId; // UploadImage의 id를 저장하기 위한 변수 추가
//    private String uploadImageUrl; // 이미지 URL을 저장하기 위한 변수 추가
//
//    public static BoardDto of(Board board) {
//        String imageUrl = null;
//        if (board.getUploadImage() != null) {
//            imageUrl = "/boards/images/" + board.getUploadImage().getSavedFilename();
//        }
//        return BoardDto.builder()
//                .id(board.getId())
//                .userLoginId(board.getUser().getLoginId())
//                .userNickname(board.getUser().getNickname())
//                .rtitle(board.getRtitle())
//                .rating(Integer.valueOf(board.getRating()))
//                .title(board.getTitle())
//                .body(board.getBody())
//                .createdAt(board.getCreatedAt())
//                .lastModifiedAt(board.getLastModifiedAt())
//                .likeCnt(board.getLikes().size())
//                .uploadImageId(board.getUploadImage() != null ? board.getUploadImage().getId() : null)
//                .uploadImageUrl(imageUrl) // 이미지 URL 설정
//                .build();
//    }
//
//
//    public static BoardDto from(Board board) {
//        return BoardDto.builder()
//                .id(board.getId())
//                .title(board.getTitle())
//                .body(board.getBody())
//                .uploadImageId(board.getUploadImage() != null ? board.getUploadImage().getId() : null)
//                .build();
//    }
//}
