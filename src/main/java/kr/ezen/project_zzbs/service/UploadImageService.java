package kr.ezen.project_zzbs.service;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.UploadImage;
import kr.ezen.project_zzbs.repository.BoardRepository;
import kr.ezen.project_zzbs.repository.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final UploadImageRepository uploadImageRepository;
    private final BoardRepository boardRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String fileDir = rootPath + "/src/main/resources/static/upload-images/";

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadImage saveImage(MultipartFile multipartFile, Board board) throws IOException {
        if (multipartFile.isEmpty()) {
            // 파일이 업로드되지 않았을 때 "/images/cats.png"의 이미지를 사용
            String defaultImageFilename = "cats.png";
            String savedFilename = UUID.randomUUID() + "." + extractExt(defaultImageFilename);

            // "/images/cats.png" 파일을 저장 경로에 복사
            Path sourcePath = Paths.get("src/main/resources/static/images", defaultImageFilename);
            Path targetPath = Paths.get(getFullPath(savedFilename));
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return uploadImageRepository.save(UploadImage.builder()
                    .originalFilename(defaultImageFilename)
                    .savedFilename(savedFilename)
                    .board(board)
                    .build());
        }

        String originalFilename = multipartFile.getOriginalFilename();
        // 원본 파일명 -> 서버에 저장된 파일명 (중복 X)
        // 파일명이 중복되지 않도록 UUID로 설정 + 확장자 유지
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        // 파일 저장
        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(UploadImage.builder()
                .originalFilename(originalFilename)
                .savedFilename(savedFilename)
                .board(board)
                .build());
    }

    @Transactional
    public void deleteImage(UploadImage uploadImage) throws IOException {
        uploadImageRepository.delete(uploadImage);
        Files.deleteIfExists(Paths.get(getFullPath(uploadImage.getSavedFilename())));
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public ResponseEntity<UrlResource> downloadImage(Long boardId) throws MalformedURLException {
        // boardId에 해당하는 게시글이 없으면 null return
        Board board = boardRepository.findById(boardId).get();
        if (board == null || board.getUploadImage() == null) {
            return null;
        }

        UrlResource urlResource = new UrlResource("file:" + getFullPath(board.getUploadImage().getSavedFilename()));

        // 업로드 한 파일명이 한글인 경우 아래 작업을 안해주면 한글이 깨질 수 있음
        String encodedUploadFileName = UriUtils.encode(board.getUploadImage().getOriginalFilename(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);

    }
}