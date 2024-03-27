package zunza.myshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.response.ProductDetails;
import zunza.myshop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public void productAdd(
		@RequestPart("productRequest") @Valid ProductRequest productRequest,
		@RequestPart("productOptionsRequest") @Valid List<ProductOptionRequest> productOptionsRequest,
		@RequestPart("mainImage") MultipartFile mainImage,
		@RequestPart("images") List<MultipartFile> images
		) throws IOException {

		productService.addProduct(productRequest, productOptionsRequest, mainImage, images);
	}

	@GetMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> productList() {
		return productService.findProductList();
	}

	@GetMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDetails productDetails(@PathVariable("productId") Long productId) {
		return productService.findProduct(productId);
	}
}
