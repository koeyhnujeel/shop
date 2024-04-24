package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.response.product_detail.ViewResponse;
import zunza.myshop.service.ViewService;

@RestController
@RequiredArgsConstructor
public class ViewController {

	private final ViewService viewService;

	@GetMapping("/products/{productId}/views")
	@ResponseStatus(HttpStatus.OK)
	public ViewResponse viewCount(@PathVariable("productId") Long productId) {
		return viewService.count(productId);
	}
}
