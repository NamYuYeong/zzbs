package kr.ezen.project_zzbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MapController {
    @GetMapping("/users/map")
    public String map(@RequestParam String keyword, Model m) {
        System.out.println("keyword = " + keyword);
        m.addAttribute("keyword", keyword);
        return "/users/map";
    }
}
