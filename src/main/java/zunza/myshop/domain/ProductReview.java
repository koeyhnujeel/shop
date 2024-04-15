package zunza.myshop.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.request.AddReviewRequest;

@Getter
@Entity
@Table(name = "PRODUCT_REVIEW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReview {

	@Id
	@Column(name = "PRODUCT_REVIEW_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Lob
	@Column(name = "PRODUCT_REVIEW_CONTENT", nullable = false)
	private String content;

	@Column(name = "PRODUCT_REVIEW_CREATED_DATE", nullable = false)
	private LocalDateTime createdAt;

	@Builder
	private ProductReview(
		User user,
		Product product,
		String content,
		LocalDateTime createdAt) {

		this.user = user;
		this.product = product;
		this.content = content;
		this.createdAt = LocalDateTime.now().withNano(0);
	}

	public static ProductReview of(
		User user,
		Product product,
		AddReviewRequest req) {

		return ProductReview.builder()
			.user(user)
			.product(product)
			.content(req.getContent())
			.build();
	}
}
