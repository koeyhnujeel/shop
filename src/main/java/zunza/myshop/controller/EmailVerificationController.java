package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.request.CodeRequest;
import zunza.myshop.request.EmailRequest;
import zunza.myshop.service.EmailVerificationService;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

	private final EmailVerificationService emailVerificationService;

	@PostMapping("/auth/request-code")
	@ResponseStatus(HttpStatus.CREATED)
	public void requestCode(
		@RequestBody EmailRequest email) {

		emailVerificationService.sendCode(email);
	}

	@PostMapping("/auth/verify-code")
	@ResponseStatus(HttpStatus.OK)
	public void verificationCode(
		@RequestBody CodeRequest req) {

		emailVerificationService.checkCode(req);
	}
}
