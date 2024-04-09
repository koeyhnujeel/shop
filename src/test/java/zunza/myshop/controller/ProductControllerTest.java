// package zunza.myshop.controller;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.List;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpMethod;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// import zunza.myshop.constant.Category;
// import zunza.myshop.repository.ProductImageRepository;
// import zunza.myshop.repository.ProductOptionRepository;
// import zunza.myshop.repository.ProductRepository;
// import zunza.myshop.request.ProductRequest;
// import zunza.myshop.request.ProductOptionRequest;
//
// @SpringBootTest
// @AutoConfigureMockMvc
// @ActiveProfiles("test")
// class ProductControllerTest {
//
// 	@Autowired
// 	private ProductRepository productRepository;
//
// 	@Autowired
// 	private ProductOptionRepository productOptionRepository;
//
// 	@Autowired
// 	private ProductImageRepository productImageRepository;
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@Autowired
// 	private ObjectMapper objectMapper;
//
// 	@BeforeEach
// 	void clean() {
// 		productRepository.deleteAll();
// 		productOptionRepository.deleteAll();
// 		productImageRepository.deleteAll();
// 	}
//
// 	@Test
// 	@DisplayName("상품 등록")
// 	void test1() throws Exception {
//
// 		//given
// 		ProductRequest productRequest = ProductRequest.builder()
// 			.productName("체크 셔츠")
// 			.price(10000)
// 			.category(Category.SHIRT)
// 			.description("테스트 상품입니다.")
// 			.build();
//
// 		ProductOptionRequest productOptionRequest = ProductOptionRequest.builder()
// 			.size("M")
// 			.color("블루")
// 			.stock(100)
// 			.build();
//
// 		List<ProductOptionRequest> optionRequests = new ArrayList<>(List.of(productOptionRequest));
//
// 		String json1 = objectMapper.writeValueAsString(productRequest);
// 		String json2 = objectMapper.writeValueAsString(optionRequests);
//
// 		MockMultipartFile productAddRequest = new MockMultipartFile("productRequest", null,
// 			"application/json", json1.getBytes(StandardCharsets.UTF_8));
//
// 		MockMultipartFile productOptionAddRequest = new MockMultipartFile("productOptionsRequest", null,
// 			"application/json", json2.getBytes(StandardCharsets.UTF_8));
//
// 		String PATH = System.getProperty("user.dir") + "/src/test/resources/static/images/test.jpg";
// 		Path path = Paths.get(PATH);
// 		byte[] content = Files.readAllBytes(path);
//
// 		MockMultipartFile mainImage = new MockMultipartFile("mainImage", "main.jpg", "image/jpg",
// 			content);
//
// 		MockMultipartFile images = new MockMultipartFile("images", "image1.jpg", "image/jpg",
// 				content);
//
// 		//expected
// 		mockMvc.perform(multipart(HttpMethod.POST, "/products/management")
// 				.file(productAddRequest)
// 				.file(productOptionAddRequest)
// 				.file(mainImage)
// 				.file(images)
// 				.file(images)
// 			)
// 			.andExpect(status().isCreated())
// 			.andDo(print());
// 	}
// }
