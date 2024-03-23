package zunza.myshop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductAddRequest;
import zunza.myshop.request.ProductOptionAddRequest;
import zunza.myshop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public void productAdd(
		@RequestPart("productAddRequest") @Valid ProductAddRequest productAddRequest,
		@RequestPart("productOptionAddRequest") @Valid ProductOptionAddRequest productOptionAddRequest,
		@RequestPart("mainImage") MultipartFile mainImage,
		@RequestPart("images") List<MultipartFile> images
		) throws IOException {

		productService.addProduct(productAddRequest, productOptionAddRequest, mainImage, images);
	}
}
