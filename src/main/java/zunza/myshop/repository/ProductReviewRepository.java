package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zunza.myshop.domain.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}
