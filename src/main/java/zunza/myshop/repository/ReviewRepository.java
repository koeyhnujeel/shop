package zunza.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select DISTINCT r "
		+ "from Review r "
		+ "join fetch r.user "
		+ "where r.product.id = :ProductId "
		+ "ORDER BY r.createdAt DESC")
	List<Review> findReviewsByProductId(@Param("ProductId") Long ProductId);
}
