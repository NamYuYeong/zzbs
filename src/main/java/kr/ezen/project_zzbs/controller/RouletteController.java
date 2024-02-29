package kr.ezen.project_zzbs.controller;

import kr.ezen.project_zzbs.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RouletteController {

    @Autowired
    private RouletteService rouletteService;

    @Async
    @GetMapping("/getRandomFood")
    public Map<String, Object> getRandomFood() {
        return rouletteService.getRandomFood();
    }
}
