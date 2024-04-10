package zunza.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	@Query("SELECT DISTINCT p "
		+ "FROM Product p "
		+ "JOIN FETCH p.images "
		+ "WHERE p.id = :productId")
	Optional<Product> findProductAndImageFetchJoin(@Param("productId") Long productId);
}
