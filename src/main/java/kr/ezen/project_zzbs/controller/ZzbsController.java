package kr.ezen.project_zzbs.controller;

import kr.ezen.project_zzbs.domain.Zzbs;
import kr.ezen.project_zzbs.repository.ZzbsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ZzbsController {

    @Autowired
    private ZzbsRepository zzbsRepository;

    @GetMapping("/users/modifycat")
    public String modifyCategoryPage(Model model) {
        List<Zzbs> zzbsList = zzbsRepository.findAll();
        model.addAttribute("zzbsList", zzbsList);
        model.addAttribute("zzbs", new Zzbs());
        return "users/modifycat";
    }

    @PostMapping("/updateZzbs")
    public String updateZzbs(Zzbs zzbs) {
        zzbsRepository.save(zzbs);
        System.out.println("수정 기능이 호출되었습니다.");
        return "redirect:/users/modifycat";
    }

    @PostMapping("/addZzbs")
    public String addZzbs(Zzbs newZzbs) {
        zzbsRepository.save(newZzbs);
        System.out.println("등록 기능이 호출되었습니다.");
        return "redirect:/users/modifycat";
    }
}
