package zunza.myshop.domain;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.constant.Category;
import zunza.myshop.request.ProductRequest;

@Getter
@Entity
@Table(name = "PRODUCTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PRODUCT_NAME", nullable = false)
	@Length(min = 1, max = 40, message = "상품명은 1 ~ 40자까지 가능합니다.")
	private String productName;

	@Column(name = "PRODUCT_PRICE", nullable = false)
	private Integer price;

	@Column(name = "PRODUCT_IMAGE", nullable = false)
	private String image;

	@Enumerated(EnumType.STRING)
	@Column(name = "PRODUCT_CATEGORY", nullable = false)
	private Category category;

	@Column(name = "PRODUCT_CREATED_DATE", nullable = false)
	private LocalDateTime createdAt;

	@Builder
	private Product(
		String productName,
		Integer price,
		String image,
		Category category) {

		this.productName = productName;
		this.price = price;
		this.image = image;
		this.category = category;
		this.createdAt = LocalDateTime.now().withNano(0);
	}

	public static Product of(ProductRequest req, String savedImage) {
		return Product.builder()
			.productName(req.getProductName())
			.price(req.getPrice())
			.image(savedImage)
			.category(req.getCategory())
			.build();
	}

	// public void modify(ProductUpdateRequest productUpdateRequest) {
	// 	this.productName = productUpdateRequest.getProductName();
	// 	this.price = productUpdateRequest.getPrice();
	// 	this.category = productUpdateRequest.getCategory();
	// 	this.description = productUpdateRequest.getDescription();
	// }
}
