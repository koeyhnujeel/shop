package zunza.myshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.request.ProductUpdateRequest;
import zunza.myshop.response.product_detail.ProductDetails;
import zunza.myshop.response.product_management.ProductDetailsForAdmin;
import zunza.myshop.response.product_management.ProductListResponseForAdmin;
import zunza.myshop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/products/management")
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

	@GetMapping("/products/management")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductListResponseForAdmin> productListForAdmin(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "30") int size,
		@RequestParam(value = "keyword", required = false) String keyword) {

		return productService.findProductListForAdmin(page, size, keyword);
	}

	@GetMapping("/products/management/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDetailsForAdmin productDetailsForAdmin(
		@PathVariable("productId") Long productId) {

		return productService.findProductForAdmin(productId);
	}

	@PatchMapping("/products/management/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public void productModify(
		@PathVariable("productId") Long productId,
		@RequestBody ProductUpdateRequest productUpdateRequest) {

		productService.productModify(productId, productUpdateRequest);
	}
}
