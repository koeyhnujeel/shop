package zunza.myshop.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyPasswordRequest {

	@NotBlank(message = "현재 비밀번호를 입력해 주세요.")
	private String oldPassword;

	@Length(min = 8, max = 16, message = "비밀번호는 최소 8자, 최대 16자입니다.")
	@NotBlank(message = "새로운 비밀번호를 입력해 주세요.")
	private String newPassword;
}
