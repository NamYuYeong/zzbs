package kr.ezen.project_zzbs.repository;

import kr.ezen.project_zzbs.domain.Zzbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzbsRepository extends JpaRepository<Zzbs, Integer> {
}
