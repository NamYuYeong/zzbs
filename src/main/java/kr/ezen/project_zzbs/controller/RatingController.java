package kr.ezen.project_zzbs.controller;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.dto.BoardDto;
import kr.ezen.project_zzbs.repository.BoardInterface;
import kr.ezen.project_zzbs.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/rating")
    public ResponseEntity<Double> getAverageRating(@RequestParam("place_name") String place_name) {
        Double averageRating = boardService.getAverageRatingByPlaceName(place_name);
        if (averageRating != null) {
            return ResponseEntity.ok(averageRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rating1")
    public ResponseEntity<List<BoardInterface>> getRandomRatingFiveBoard(@RequestParam("place_name") String place_name) {
        List<BoardInterface> boardDto = boardService.getRandomRatingFiveBoard(place_name);
        if (boardDto != null) {
            return ResponseEntity.ok(boardDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rating3")
    public ResponseEntity<Double> getAverageRating2(@RequestParam("place_name") String place_name) {
        Double averageRating = boardService.getAverageRatingByPlaceName(place_name);
        if (averageRating != null) {
            return ResponseEntity.ok(averageRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}