package kr.ezen.project_zzbs.service;

import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.Zzbs;
import kr.ezen.project_zzbs.dto.UserDto;
import kr.ezen.project_zzbs.dto.ZzbsDTO;
import kr.ezen.project_zzbs.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CategoryRepository categoryRepository;
    /*admin 카테고리 추가*/

    public Page<Zzbs> findAllByFood(String keyword, PageRequest pageRequest) {
        return categoryRepository.findAllByFoodContains(keyword, pageRequest);
    }

    public void CategoryInsert(Zzbs dto, BindingResult bindingResult) {
        categoryRepository.save(dto);
    }

    public Zzbs categoryInfo(long bid) {
        return categoryRepository.findByBid(bid).get();
    }

    // 카테코리 수정
    @Transactional
    public void categoryModify(Zzbs dto){
        Zzbs findZzbs = categoryRepository.findByBid(dto.getBid()).get();
        findZzbs.setNation(dto.getNation());
        findZzbs.setFood(dto.getFood());
    }
    @Transactional
    public void categoryDelete(long bid) {
        categoryRepository.deleteById(bid);
    }
}
