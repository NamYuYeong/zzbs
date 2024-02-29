package kr.ezen.project_zzbs.controller;


import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.Zzbs;
import kr.ezen.project_zzbs.dto.*;
import kr.ezen.project_zzbs.service.AdminService;
import kr.ezen.project_zzbs.service.BoardService;
import kr.ezen.project_zzbs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final BoardService boardService;

    @GetMapping("/adminHome")
    public String adminPage(@RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "") String keyword,
                            Model model) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<User> users = userService.findAllByNickname(keyword, pageRequest);

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/admin";
    }

    @GetMapping("/adminHome/{userId}")
    public String adminChangeRole(@PathVariable Long userId,
                                  @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "") String keyword) throws UnsupportedEncodingException {
        userService.changeRole(userId);
        return "redirect:?page=" + page + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
    }


    /*admin category추가*/
    @GetMapping("/foodCategoryList")
    public String foodCategoryList(@RequestParam(required = false, defaultValue = "1") int page,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("bid").descending());
        Page<Zzbs> food = adminService.findAllByFood(keyword, pageRequest);

        model.addAttribute("foods", food);
        model.addAttribute("keyword", keyword);
        return "/users/foodCategoryList";
    }

    @GetMapping("/foodCategoryList/{food}")
    public String foodCategoryList(@PathVariable Long food,
                                   @RequestParam(required = false, defaultValue = "1") int page,
                                   @RequestParam(required = false, defaultValue = "") String keyword) throws UnsupportedEncodingException {

        return "redirect:/foodCategoryList?page=" + page + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
    }

    ////    카테고리 추가
    @GetMapping("/CategoryInsert")
    public String CategoryInsert(Model model) {
        model.addAttribute("CategoryInsert", new ZzbsDTO());
        return "users/CategoryInsert";
    }

    @PostMapping("/CategoryInsert")
    public String CategoryInsert(Zzbs dto, BindingResult bindingResult, Model model) {


        adminService.CategoryInsert(dto, bindingResult);
        model.addAttribute("message", "카테고리등록에 성공했습니다!");
        model.addAttribute("nextUrl", "/user/foodCategoryList");

        return "redirect:/admin/foodCategoryList";
    }

    // 카테코리 수정
    @PostMapping("/CategoryEdit")
    @ResponseBody
    public ResponseDTO<?> CategoryEdit(@RequestBody Zzbs dto){
        System.out.println("dto.getBid() = " + dto.getBid());
        adminService.categoryModify(dto);

        return new ResponseDTO<>(HttpStatus.OK.value(), "수정 완료!!");
    }

    // 카테코리 삭제

    @DeleteMapping("/CategoryDelete/{bid}")
    public @ResponseBody ResponseDTO<?> Categorydelete(@PathVariable long bid){
        adminService.categoryDelete(bid);

        return new ResponseDTO<>(HttpStatus.OK.value(), bid+ "번 음식 삭제 완료!!");
    }

}
