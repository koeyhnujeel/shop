package zunza.myshop.config.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import zunza.myshop.response.ErrorResponse;

@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(String.valueOf(401))
			.message("잘못된 요청입니다.")
			.errorMap(new HashMap<>(Map.of("error", "로그인이 필요한 서비스 입니다.")))
			.build();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(401);
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
