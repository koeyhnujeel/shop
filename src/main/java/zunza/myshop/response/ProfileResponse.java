package zunza.myshop.response;

import lombok.Getter;
import zunza.myshop.domain.User;

@Getter
public class ProfileResponse {

	private String name;
	private String email;
	private String nickname;
	private String address;

	private ProfileResponse(
		String name,
		String email,
		String nickname,
		String address) {

		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.address = address;
	}

	public static ProfileResponse from(User user) {
		return new ProfileResponse(
			user.getName(),
			user.getEmail(),
			user.getNickname(),
			user.getAddress()
		);
	}
}
