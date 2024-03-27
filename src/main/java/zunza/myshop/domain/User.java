package zunza.myshop.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_EMAIL", nullable = false)
	private String email;

	@Column(name = "USER_PASSWORD", nullable = false)
	private String password;

	@Column(name = "USER_NICKNAME", nullable = false)
	private String nickname;

	@Column(name = "USER_ADDRESS", nullable = false)
	private String address;

	@Column(name = "USER_REGISTRATION_DATE")
	private LocalDateTime registrationDate;

	@Builder
	private User(
		String email,
		String password,
		String nickname,
		String address) {

		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.address = address;
		this.registrationDate = LocalDateTime.now().withNano(0);
	}
}
