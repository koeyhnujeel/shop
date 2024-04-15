package zunza.myshop.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import zunza.myshop.constant.LikeStatus;

@Getter
@Entity
@Table(name = "LIKES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

	@Id
	@Column(name = "LIKE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Enumerated(EnumType.STRING)
	@Column(name = "LIKE_STATUS", nullable = false)
	private LikeStatus likeStatus;

	@Builder
	private Like(User user, Product product) {
		this.user = user;
		this.product = product;
		this.likeStatus = LikeStatus.TRUE;
	}

	public static Like of(User user, Product product) {
		return Like.builder()
			.user(user)
			.product(product)
			.build();
	}

	public void updateLikeStatus() {
		this.likeStatus = this.likeStatus.equals(LikeStatus.FALSE) ? LikeStatus.TRUE : LikeStatus.FALSE;
	}
}
