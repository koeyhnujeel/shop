package zunza.myshop.exception;

public class ProductOptionNotFoundException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public ProductOptionNotFoundException(Long productOptionId) {
		super(MESSAGE);
		addError("product option ID: " + productOptionId, "존재하지 않는 상품 옵션입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
