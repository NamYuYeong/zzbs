package kr.ezen.project_zzbs.controller;

import kr.ezen.project_zzbs.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/header")
public class HeaderController {

    private final BoardService boardService;

    public HeaderController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/images")
    public ResponseEntity<List<String>> getHeaderImages() {
        List<String> imageUrls = boardService.getHeaderImages();
        return ResponseEntity.ok().body(imageUrls);
    }
}

