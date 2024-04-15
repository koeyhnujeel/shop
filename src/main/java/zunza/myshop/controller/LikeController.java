package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.response.product_detail.LikeResponse;
import zunza.myshop.service.LikeService;

@RestController
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@GetMapping("/products/{productId}/likes")
	@ResponseStatus(HttpStatus.OK)
	public LikeResponse likeCount(
		@AuthenticationPrincipal Long userId,
		@PathVariable("productId") Long productId) {

		return likeService.count(userId, productId);
	}
}
