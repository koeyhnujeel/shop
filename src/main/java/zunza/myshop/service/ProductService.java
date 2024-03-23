package zunza.myshop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.Product;
import zunza.myshop.domain.ProductImage;
import zunza.myshop.domain.ProductOption;
import zunza.myshop.repository.ProductImageRepository;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.request.ProductAddRequest;
import zunza.myshop.request.ProductOptionAddRequest;
import zunza.myshop.util.ImageUtil;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final ProductImageRepository productImageRepository;
	private final ImageUtil imageUtil;


	public void addProduct(
		ProductAddRequest productAddRequest,
		ProductOptionAddRequest productOptionAddRequest,
		MultipartFile mainImage,
		List<MultipartFile> images
		) throws IOException {

		Product product = productAddRequest.toEntity();
		productRepository.save(product);

		ProductOption productOption = productOptionAddRequest.toEntity();
		productOption.setRelation(product);
		productOptionRepository.save(productOption);

		List<ProductImage> productImages = imageUtil.convertToEntitiesWithResizeAndSave(mainImage, images);
		productImages.forEach(productImage -> productImageRepository.save(productImage.setRelation(product)));
	}
}
