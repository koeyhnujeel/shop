package zunza.myshop.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import zunza.myshop.constant.Category;
import zunza.myshop.domain.Product;
import zunza.myshop.repository.ProductImageRepository;
import zunza.myshop.repository.ProductOptionRepository;
import zunza.myshop.repository.ProductRepository;
import zunza.myshop.request.ProductAddRequest;
import zunza.myshop.request.ProductOptionAddRequest;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductService productService;

	@BeforeEach
	void clean() {
		productRepository.deleteAll();
		productOptionRepository.deleteAll();
		productImageRepository.deleteAll();
	}

	@Test
	@DisplayName("상품 등록")
	@Transactional
	void test1() throws IOException {
		//given
		ProductAddRequest productAddRequest = ProductAddRequest.builder()
			.productName("체크 셔츠")
			.price(10000)
			.category(Category.SHIRT)
			.description("테스트 상품입니다.")
			.build();

		ProductOptionAddRequest productOptionAddRequest = ProductOptionAddRequest.builder()
			.size("M")
			.color("블루")
			.stock(100)
			.build();

		String PATH = System.getProperty("user.dir") + "/src/test/resources/static/images/test.jpg";
		Path path = Paths.get(PATH);
		byte[] content = Files.readAllBytes(path);

		MultipartFile mainImage = new MockMultipartFile("main", "main.jpeg", "image/jpeg",
			content);

		List<MultipartFile> images = new ArrayList<>(List.of(
			new MockMultipartFile("image1", "image1.jpeg", "image/jpeg",
				content),
			new MockMultipartFile("image2", "image2.jpeg", "image/jpeg",
				content)
		));

		//when
		productService.addProduct(productAddRequest, productOptionAddRequest,mainImage, images);

		//then
		Product product = productRepository.findAll().get(0);

		assertEquals("체크 셔츠", product.getProductName());
		assertEquals(10000, product.getPrice());
		assertEquals(Category.SHIRT, product.getCategory());
		assertEquals("테스트 상품입니다.", product.getDescription());
		assertEquals("M", product.getOptions().get(0).getSize());
		assertEquals("블루", product.getOptions().get(0).getColor());
		assertEquals(100, product.getOptions().get(0).getStock());
		assertEquals(4, product.getImages().size());
	}
}
