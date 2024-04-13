package zunza.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zunza.myshop.request.JoinRequest;
import zunza.myshop.service.JoinService;

@RestController
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;

	@PostMapping("/join")
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(
		@RequestBody @Valid JoinRequest req) {

		joinService.userAdd(req);
	}

	@GetMapping("/join/email/exists")
	@ResponseStatus(HttpStatus.OK)
	public void checkEmail(
		@RequestParam("email") String email) {

		joinService.emailCheck(email);
	}

	@GetMapping("/join/nickname/exists")
	@ResponseStatus(HttpStatus.OK)
	public void checkNickname(
		@RequestParam("nickname") String nickname) {

		joinService.nicknameCheck(nickname);
	}
}
