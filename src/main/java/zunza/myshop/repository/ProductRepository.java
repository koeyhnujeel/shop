package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
