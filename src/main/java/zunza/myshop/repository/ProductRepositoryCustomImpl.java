package zunza.myshop.repository;

import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.QProduct;
import zunza.myshop.domain.QProductImage;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Product> findProductsAndImageByCriteria(String criteria) {
		QProduct product = QProduct.product;
		QProductImage productImage = QProductImage.productImage;

		return jpaQueryFactory.selectFrom(product)
			.leftJoin(product.images, productImage).fetchJoin()
			.distinct()
			.orderBy(getOrderSpecifier(criteria))
			.limit(14)
			.fetch();
	}

	private OrderSpecifier getOrderSpecifier(String criteria) {
		if (criteria.equals("sales")) return new OrderSpecifier<>(Order.DESC, QProduct.product.salesRate);
		else if (criteria.equals("latest")) return new OrderSpecifier<>(Order.DESC, QProduct.product.createdAt);
		return null;
	};
}
