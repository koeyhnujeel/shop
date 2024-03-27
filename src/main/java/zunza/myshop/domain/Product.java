package zunza.myshop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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

	@Column(name = "PRODUCT_NAME", nullable = false, length = 40)
	private String productName;

	@Column(name = "PRODUCT_PRICE", nullable = false)
	private Integer price;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductOption> options = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "PRODUCT_CATEGORY", nullable = false)
	private Category category;

	@Lob
	@Column(name = "PRODUCT_DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "PRODUCT_SALES_RATE")
	private Integer salesRate;

	@Column(name = "PRODUCT_LIKE_COUNT")
	private Integer likeCount;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> images = new ArrayList<>();

	@Column(name = "PRODUCT_CREATED_DATE", nullable = false)
	private LocalDateTime createdAt;

	@Builder
	private Product(
		String productName,
		Integer price,
		Category category,
		String description) {

		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.salesRate = 0;
		this.likeCount = 0;
		this.createdAt = LocalDateTime.now().withNano(0);
	}

	public static Product from(ProductRequest productRequest) {
		return Product.builder()
			.productName(productRequest.getProductName())
			.price(productRequest.getPrice())
			.category(productRequest.getCategory())
			.description(productRequest.getDescription())
			.build();
	}
}
