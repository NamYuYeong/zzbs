package kr.ezen.project_zzbs.controller;

import kr.ezen.project_zzbs.domain.Blog;
import kr.ezen.project_zzbs.dto.PlaceDTO;
import kr.ezen.project_zzbs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogReviewController {
//    private final BlogService blogService;
//
//    public BlogReviewController(BlogService blogService) {
//        this.blogService = blogService;
//    }

    @Autowired
    private BlogService blogService;

    @GetMapping("/users/resDetail")
    public String blog(@RequestParam String keyword, PlaceDTO placeDTO, Model m) throws Exception {
        List<Blog> blogList = blogService.getBlogDatas(placeDTO);
        m.addAttribute("blog", blogList);
        m.addAttribute("keyword", keyword);

        return "/users/resDetail";
    }

}
