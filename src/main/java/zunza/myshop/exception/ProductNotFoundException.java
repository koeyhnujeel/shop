package zunza.myshop.exception;

public class ProductNotFoundException extends CustomException {

	private static final String MESSAGE = "잘못된 요청입니다.";

	public ProductNotFoundException(Long productId) {
		super(MESSAGE);
		addError("product ID: " + productId, "존재하지 않는 상품입니다.");
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}
