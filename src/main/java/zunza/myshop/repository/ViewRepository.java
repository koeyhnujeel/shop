package zunza.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.View;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
	Optional<View> findByUserIdAndProductId(Long userId, Long productId);

	Long countByProductId(Long productId);
}
