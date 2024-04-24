package zunza.myshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.domain.User;
import zunza.myshop.exception.UserNotFoundException;
import zunza.myshop.jwt.UserPrincipal;
import zunza.myshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(email));

		return new UserPrincipal(user);
	}


}
