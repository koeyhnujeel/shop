package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import zunza.myshop.exception.CustomException;
import zunza.myshop.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse invalidRequest(MethodArgumentNotValidException e) {
		ErrorResponse response = ErrorResponse.builder()
			.message("잘못된 요청입니다.")
			.code("400")
			.build();

		for (FieldError fieldError : e.getFieldErrors()) {
			response.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return response;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ErrorResponse missingRequestPart(MissingServletRequestPartException e) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.message("잘못된 요청입니다.")
			.code("400")
			.build();

		if (e.getRequestPartName().equals("mainImage")) {
			errorResponse.addError("mainImage", "대표 이미지를 업로드 해주세요.");
		} else if (e.getRequestPartName().equals("images")) {
			errorResponse.addError("images", "서브 이미지를 업로드 해주세요.");
		}

		return errorResponse;
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customException(CustomException e) {
		int code = e.getStatusCode();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.code(String.valueOf(code))
			.errorMap(e.getErrors())
			.build();

		return ResponseEntity.status(code)
			.body(errorResponse);
	}
}
