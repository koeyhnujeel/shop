package zunza.myshop.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.repository.ViewRepository;
import zunza.myshop.response.product_detail.ViewResponse;

@Service
@RequiredArgsConstructor
public class ViewService {

	private final ViewRepository viewRepository;

	public ViewResponse count(Long productId) {
		Long count = viewRepository.countByProductId(productId);
		return ViewResponse.from(count);
	}
}
