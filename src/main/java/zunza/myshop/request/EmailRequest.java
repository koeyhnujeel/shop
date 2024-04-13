package zunza.myshop.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailRequest {

	@Email(message = "잘못된 이메일 형식입니다.")
	@NotBlank(message = "이메일을 입력해 주세요.")
	private String email;
}
