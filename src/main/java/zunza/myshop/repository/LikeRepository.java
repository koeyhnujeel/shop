package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("SELECT COUNT(l) "
		+ "FROM Like l "
		+ "WHERE l.product.id = :productId "
		+ "AND l.likeStatus = 'TRUE'")
	Long countLike(@Param("productId") Long productId);


	@Query("SELECT CASE WHEN COUNT(l) > 0 "
		+ "THEN true ELSE false END "
		+ "FROM Like l "
		+ "WHERE l.user.id = :userId "
		+ "AND l.product.id = :productId "
		+ "AND l.likeStatus = 'TRUE'")
	boolean isLiked(@Param("userId") Long userId, @Param("productId") Long productId);
}
