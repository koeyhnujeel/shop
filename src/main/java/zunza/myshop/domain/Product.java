package zunza.myshop.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.constant.Category;

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

	@Column(name = "PRODUCT_CATEGORY")
	private Category category;

	@Lob
	@Column(name = "PRODUCT_DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "PRODUCT_CREATED_DATE", nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public Product(
		String productName,
		Integer price,
		Category category,
		String description) {

		this.productName = productName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.createdAt = LocalDateTime.now().withNano(0);
	}
}
