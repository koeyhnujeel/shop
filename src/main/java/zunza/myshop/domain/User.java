package zunza.myshop.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zunza.myshop.constant.Role;
import zunza.myshop.request.JoinRequest;
import zunza.myshop.request.ModifyProfileRequest;

@Getter
@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_NAME", nullable = false)
	private String name;

	@Column(name = "USER_EMAIL", nullable = false)
	private String email;

	@Column(name = "USER_PASSWORD", nullable = false)
	private String password;

	@Column(name = "USER_NICKNAME", nullable = false)
	private String nickname;

	@Column(name = "USER_ADDRESS", nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_ROLE", nullable = false)
	private Role role;

	@Column(name = "USER_REGISTRATION_DATE")
	private LocalDateTime registrationDate;

	@Builder
	private User(
		String name,
		String email,
		String password,
		String nickname,
		String address,
		Role role) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.address = address;
		this.registrationDate = LocalDateTime.now().withNano(0);
		this.role = Role.USER;
	}

	public static User of(JoinRequest joinRequest, String encodePassword) {
		return User.builder()
			.name(joinRequest.getName())
			.email(joinRequest.getEmail())
			.password(encodePassword)
			.nickname(joinRequest.getNickname())
			.address(joinRequest.getAddress())
			.build();
	}

	public void profileUpdate(ModifyProfileRequest req) {
		this.name = req.getName();
		this.email = req.getEmail();
		this.nickname = req.getNickname();
		this.address = req.getAddress();
	}
}
