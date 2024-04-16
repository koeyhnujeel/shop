package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zunza.myshop.request.AuthRequest;
import zunza.myshop.request.ModifyPasswordRequest;
import zunza.myshop.request.ModifyProfileRequest;
import zunza.myshop.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/user/profile/auth")
	@ResponseStatus(HttpStatus.OK)
	public void auth(
		@AuthenticationPrincipal Long userId,
		@RequestBody AuthRequest req) {

		userService.checkPassword(userId, req);
	}

	@PatchMapping("/user/modify-profile")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void profileModify(
		@AuthenticationPrincipal Long userId,
		@RequestBody ModifyProfileRequest req) {

		userService.modifyProfile(userId, req);
	}

	@PatchMapping("/user/modify-password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void passwordModify(
		@AuthenticationPrincipal Long userId,
		@RequestBody ModifyPasswordRequest req) {

		userService.modifyPassword(userId, req);
	}
}
