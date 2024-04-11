package zunza.myshop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.User;
import zunza.myshop.exception.ExistsNicknameException;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.request.JoinRequest;

@Service
@RequiredArgsConstructor
public class JoinService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void userAdd(JoinRequest req) {
		String encodePassword = passwordEncoder.encode(req.getPassword());
		User user = User.of(req, encodePassword);
		userRepository.save(user);
	}

	public void nicknameCheck(String nickname) {
		Boolean result = userRepository.existsByNickname(nickname);
		if(result) throw new ExistsNicknameException();
	}
}
