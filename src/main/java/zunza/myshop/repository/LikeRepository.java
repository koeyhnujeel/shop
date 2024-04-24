package zunza.myshop.repository;

import java.util.List;
import java.util.Optional;

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

	Optional<Like> findByUserIdAndProductId(Long userId, Long productId);

	@Query("SELECT l FROM "
		+ "Like l "
		+ "JOIN FETCH l.product p "
		+ "WHERE l.user.id = :userId "
		+ "AND l.likeStatus = 'TRUE'")
	List<Like> findUserLike(@Param("userId") Long userId);
}
