package zunza.myshop.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductRequest;
import zunza.myshop.request.ProductUpdateRequest;
import zunza.myshop.response.product_detail.ProductDetailsResponse;
import zunza.myshop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/admin/products")
	@ResponseStatus(HttpStatus.CREATED)
	public void productAdd(
		@RequestPart("productRequest") @Valid ProductRequest req,
		@RequestPart("Image") MultipartFile image
		) throws IOException {

		productService.addProduct(req, image);
	}

	// @GetMapping("/products")
	// @ResponseStatus(HttpStatus.OK)
	// public Map<String, Object> productList() {
	// 	return productService.findProductList();
	// }

	@GetMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDetailsResponse productDetails(
		@AuthenticationPrincipal Long userId,
		@PathVariable("productId") Long productId) {

		return productService.findProduct(userId, productId);
	}

	// @GetMapping("/admin/products")
	// @ResponseStatus(HttpStatus.OK)
	// public List<ProductListResponseForAdmin> productListForAdmin(
	// 	@RequestParam(value = "page", defaultValue = "1") int page,
	// 	@RequestParam(value = "size", defaultValue = "30") int size,
	// 	@RequestParam(value = "keyword", required = false) String keyword) {
	//
	// 	return productService.findProductListForAdmin(page, size, keyword);
	// }

	// @GetMapping("/admin/products/{productId}")
	// @ResponseStatus(HttpStatus.OK)
	// public ProductDetailsForAdmin productDetailsForAdmin(
	// 	@PathVariable("productId") Long productId) {
	//
	// 	return productService.findProductForAdmin(productId);
	// }

	@PatchMapping("/admin/products/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void productModify(
		@PathVariable("productId") Long productId,
		@RequestBody ProductUpdateRequest productUpdateRequest) {

		productService.modifyProduct(productId, productUpdateRequest);
	}

	@DeleteMapping("/admin/products/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void productRemove(
		@PathVariable("productId") Long productId) {

		productService.removeProduct(productId);
	}
}
