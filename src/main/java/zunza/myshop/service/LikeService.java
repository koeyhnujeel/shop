package zunza.myshop.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.repository.LikeRepository;
import zunza.myshop.response.product_detail.LikeResponse;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;

	public LikeResponse count(Long userId, Long productId) {
		Long count = likeRepository.countLike(productId);
		boolean liked = false;

		if (Objects.nonNull(userId)) {
			liked = likeRepository.isLiked(userId, productId);
		}

		return LikeResponse.of(count, liked);
	}
}
