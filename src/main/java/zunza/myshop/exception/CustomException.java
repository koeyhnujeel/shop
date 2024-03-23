package zunza.myshop.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

	private Map<String, String> errors = new HashMap<>();

	public CustomException(String message) {
		super(message);
	}

	public abstract int getCode();

	public void addError(String errorField, String message) {
		errors.put(errorField, message);
	}
}
