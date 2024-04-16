package zunza.myshop.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyProfileRequest {

	private String name;

	@Length(min = 2, max = 15, message = "닉네임은 최소 2자, 최대 15자입니다.")
	private String nickname;

	@Email(message = "잘못된 이메일 형식입니다.")
	private String email;

	private String address;
}
