package zunza.myshop.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeRequest {

	private String email;
	private String code;
}
