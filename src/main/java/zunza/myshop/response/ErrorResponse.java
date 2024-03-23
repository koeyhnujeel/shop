package zunza.myshop.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private String message;
	private String code;
	private Map<String, String> errors;

	@Builder
	public ErrorResponse(String message, String code, Map<String, String> errorMap) {
		this.message = message;
		this.code = code;
		this.errors = errorMap != null ? errorMap : new HashMap<>();
	}

	public void addError(String errorFiled, String message) {
		errors.put(errorFiled, message);
	}
}
