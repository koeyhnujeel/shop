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
@Table(name = "PRODUCT_IMAGES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_IMAGE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(name = "PRODUCT_IMAGE_NAME")
	private String imageName;

	@Column(name = "PRODUCT_IMAGE_URL")
	private String imageUrl;

	@Builder
	private ProductImage(
		Product product,
		String imageName,
		String imageUrl) {

		this.product = product;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}

	public static ProductImage of(String imageName, String imageUrl) {
		return ProductImage.builder()
			.imageName(imageName)
			.imageUrl(imageUrl)
			.build();
	}

	public ProductImage setRelation(Product product) {
		this.product = product;
		product.getImages().add(this);
		return this;
	}
}
