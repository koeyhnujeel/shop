package zunza.myshop.config.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import zunza.myshop.response.ErrorResponse;

@RequiredArgsConstructor
public class Http403Handler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(String.valueOf(403))
			.message("잘못된 요청입니다.")
			.errorMap(new HashMap<>(Map.of("error", "접근 권한이 없습니다.")))
			.build();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(403);
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
