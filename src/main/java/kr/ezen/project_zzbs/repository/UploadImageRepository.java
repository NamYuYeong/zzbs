package kr.ezen.project_zzbs.repository;


import kr.ezen.project_zzbs.domain.UploadImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadImageRepository extends JpaRepository<UploadImage, Long> {
}