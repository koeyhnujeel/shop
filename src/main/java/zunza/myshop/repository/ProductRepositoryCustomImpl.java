package zunza.myshop.repository;

import static java.lang.Math.*;

import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.QProduct;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	// private final JPAQueryFactory jpaQueryFactory;

	// @Override
	// public List<Product> findProductsAndImageByCriteria(String criteria) {
	// 	QProduct product = QProduct.product;
	// 	QProductImage productImage = QProductImage.productImage;
	//
	// 	return jpaQueryFactory.selectFrom(product)
	// 		.leftJoin(product.images, productImage).fetchJoin()
	// 		.distinct()
	// 		.orderBy(getOrderSpecifier(criteria))
	// 		.limit(14)
	// 		.fetch();
	// }

	// private OrderSpecifier getOrderSpecifier(String criteria) {
	// 	if (criteria.equals("sales")) return new OrderSpecifier<>(Order.DESC, QProduct.product.salesRate);
	// 	else if (criteria.equals("latest")) return new OrderSpecifier<>(Order.DESC, QProduct.product.createdAt);
	// 	return null;
	// };

	// @Override
	// public List<Product> findProductAndImageForAdmin(
	// 	int page, int size, String keyword) {
	//
	// 	QProduct product = QProduct.product;
	// 	QProductImage productImage = QProductImage.productImage;
	//
	// 	return jpaQueryFactory.selectFrom(product)
	// 		.leftJoin(product.images, productImage).fetchJoin()
	// 		.where(likeKeyword(keyword))
	// 		.offset(getOffset(page, size))
	// 		.limit(size)
	// 		.fetch();
	// }

	private BooleanExpression likeKeyword(String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder(
			"%"+keyword.replace(" ", "%")+"%");

		return QProduct.product.productName.like(stringBuilder.toString());
	}

	private long getOffset(int page, int size) {
		return (long) (max(1, page) - 1) * max(10, size);
	}
}
