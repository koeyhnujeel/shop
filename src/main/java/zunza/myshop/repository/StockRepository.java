package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
