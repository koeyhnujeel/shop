package zunza.myshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.service.ProductOptionService;

@RestController
@RequiredArgsConstructor
public class ProductOptionController {

	private final ProductOptionService productOptionService;

	@PostMapping("/products/management/{productId}/productOptions")
	@ResponseStatus(HttpStatus.CREATED)
	public void productOptionsAdd(
		@PathVariable("productId") Long productId,
		@RequestBody List<ProductOptionRequest> productOptionRequests) {

		productOptionService.addProductOptions(productId, productOptionRequests);
	}
}
