package zunza.myshop.jwt;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import zunza.myshop.constant.Role;

@Getter
public class UserPrincipal extends User {

	private final Long userId;

	public UserPrincipal(zunza.myshop.domain.User user) {
		super(
			user.getEmail(),
			user.getPassword(),
			List.of(
				new SimpleGrantedAuthority(user.getRole().equals(Role.USER) ? "ROLE_USER" : "ROLE_ADMIN")
			)
		);

		this.userId = user.getId();
	}
}
