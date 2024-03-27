package zunza.myshop.repository;

import java.util.List;

import zunza.myshop.domain.Product;

public interface ProductRepositoryCustom {

	List<Product> findProductsAndImageByCriteria(String criteria);
}
