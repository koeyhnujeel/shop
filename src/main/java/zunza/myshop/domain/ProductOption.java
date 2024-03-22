package zunza.myshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "PRODUCT_OPTIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_OPTION_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(name = "PRODUCT_SIZE", nullable = false)
	private String size;

	@Column(name = "PRODUCT_COLOR")
	private String color;

	@Column(name = "PRODUCT_OPTION_STOCK", nullable = false)
	private Integer stock;

	@Builder
	public ProductOption(
		Product product,
		String size,
		String color,
		Integer stock) {

		this.product = product;
		this.size = size;
		this.color = color;
		this.stock = stock;
	}
}
