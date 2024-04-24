package zunza.myshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id", nullable = false)
	private Long id;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "quantity")
	private Integer quantity;

	@Builder
	private Stock(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public static Stock of(Long productId, Integer quantity) {
		return Stock.builder()
			.productId(productId)
			.quantity(quantity)
			.build();
	}
}
