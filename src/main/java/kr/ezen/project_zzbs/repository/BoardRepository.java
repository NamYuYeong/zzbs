package kr.ezen.project_zzbs.repository;

import kr.ezen.project_zzbs.domain.Board;
import kr.ezen.project_zzbs.domain.enumClass.BoardCategory;
import kr.ezen.project_zzbs.domain.enumClass.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByCategoryAndUserUserRoleNot(BoardCategory category, UserRole userRole, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndRtitleContainsAndUserUserRoleNot(BoardCategory category, String rtitle, UserRole userRole, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndTitleContainsAndUserUserRoleNot(BoardCategory category, String title, UserRole userRole, PageRequest pageRequest);
    Page<Board> findAllByCategoryAndUserNicknameContainsAndUserUserRoleNot(BoardCategory category, String nickname, UserRole userRole, PageRequest pageRequest);
    List<Board> findAllByUserLoginId(String loginId);
    List<Board> findAllByCategoryAndUserUserRole(BoardCategory category, UserRole userRole);
    Long countAllByUserUserRole(UserRole userRole);
    Long countAllByCategoryAndUserUserRoleNot(BoardCategory category, UserRole userRole);
    @Query("SELECT AVG(b.rating) FROM Board b WHERE b.rtitle = :place_name")
    Double findAverageRatingByPlaceName(@Param("place_name") String placeName);

    /* zzbs 리뷰 */
//    @Query(value = "SELECT b.title, b.body, b.upload_image_id FROM Board b WHERE b.rating = '5' and b.rtitle = :place_name ORDER BY RAND() limit 1", nativeQuery = true)

    @Query(value = "SELECT b.id, b.title, b.body, b.rating, u.id as upload_image_id, u.saved_filename FROM Board b INNER JOIN Upload_image u ON b.upload_image_id = u.id WHERE b.rtitle = :place_name ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<BoardInterface> findRandomRatingFiveBoard(@Param("place_name") String placeName);

    List<Board> findByRating(String rating);
}
