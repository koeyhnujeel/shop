package zunza.myshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.request.ProductOptionRequest;
import zunza.myshop.request.ProductOptionUpdateRequest;
import zunza.myshop.service.ProductOptionService;

@RestController
@RequiredArgsConstructor
public class ProductOptionController {

	private final ProductOptionService productOptionService;

	@PostMapping("/products/management/{productId}/product-options")
	@ResponseStatus(HttpStatus.CREATED)
	public void productOptionsAdd(
		@PathVariable("productId") Long productId,
		@RequestBody List<ProductOptionRequest> productOptionRequests) {

		productOptionService.addProductOptions(productId, productOptionRequests);
	}

	@PatchMapping("/product-options/{product-optionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void productOptionModify(
		@PathVariable("product-optionId") Long productOptionId,
		@RequestBody ProductOptionUpdateRequest productOptionUpdateRequest) {

		productOptionService.modifyProductOption(productOptionId, productOptionUpdateRequest);
	}
}
