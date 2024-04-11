package zunza.myshop.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

	@Email(message = "잘못된 이메일 형식입니다.")
	@NotBlank(message = "이메일을 입력해 주세요.")
	private String email;

	@Length(min = 8, max = 16, message = "비밀번호는 최소 8자, 최대 16자입니다.")
	@NotBlank(message = "비밀번호를 입력해 주세요.")
	private String password;

	@Length(min = 2, max = 15, message = "닉네임은 최소 2자, 최대 15자입니다.")
	@NotBlank(message = "닉네임을 입력해 주세요.")
	private String nickname;

	@NotBlank(message = "주소를 입력해 주세요.")
	private String address;
}
