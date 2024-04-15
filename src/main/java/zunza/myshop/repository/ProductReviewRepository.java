package zunza.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

	@Query("select DISTINCT r "
		+ "from ProductReview r "
		+ "join fetch r.user "
		+ "where r.product.id = :ProductId "
		+ "ORDER BY r.createdAt DESC")
	List<ProductReview> findReviewsByProductId(@Param("ProductId") Long ProductId);
}
