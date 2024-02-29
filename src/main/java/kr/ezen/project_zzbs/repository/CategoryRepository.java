package kr.ezen.project_zzbs.repository;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import kr.ezen.project_zzbs.domain.User;
import kr.ezen.project_zzbs.domain.Zzbs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Zzbs, Long> {


    /*admin*/
    Page<Zzbs> findAllByFoodContains(String food, PageRequest pageRequest);
    Boolean existsByFood(String food);

    Optional<Zzbs> findByBid(Long bid);

//    Optional <Zzbs> findByBid(int bid);
//    Boolean existsByBid(int bid);
//
//    boolean existsByfood(String food);
}
