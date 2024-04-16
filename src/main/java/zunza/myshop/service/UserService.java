package zunza.myshop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.User;
import zunza.myshop.exception.NotMatchPasswordException;
import zunza.myshop.exception.PermissionDeniedException;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.repository.UserRepository;
import zunza.myshop.request.AuthRequest;
import zunza.myshop.request.ModifyPasswordRequest;
import zunza.myshop.request.ModifyProfileRequest;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void checkPassword(Long userId, AuthRequest req) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		boolean matches = passwordEncoder.matches(req.getPassword(), user.getPassword());
		if (!matches) {
			throw new PermissionDeniedException();
		}
	}
	@Transactional
	public void modifyProfile(Long userId, ModifyProfileRequest req) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		user.profileUpdate(req);
	}

	@Transactional
	public void modifyPassword(Long userId, ModifyPasswordRequest req) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		boolean matches = passwordEncoder.matches(req.getOldPassword(), user.getPassword());
		if (!matches) {
			throw new NotMatchPasswordException();
		}

		String encode = passwordEncoder.encode(req.getNewPassword());
		user.passwordUpdate(encode);
	}
}
