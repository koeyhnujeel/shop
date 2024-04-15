package zunza.myshop.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zunza.myshop.response.ErrorResponse;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;
	private final JWTUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		//클라이언트 요청에서 username, password 추출
		String email = request.getParameter("email");
		String password = obtainPassword(request);

		//스프링 시큐리티에서 email과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, null);

		//token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(token);
	}

	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
		log.info("로그인 성공");

		UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
		Long userId = principal.getUserId();

		Iterator<GrantedAuthority> iterator = principal.getAuthorities().iterator();
		GrantedAuthority authority = iterator.next();
		String role = authority.getAuthority();

		String jwt = jwtUtil.createJwt(userId, role, 60 * 15L);

		response.addHeader("Authorization", "Bearer " + jwt);
		response.setStatus(200);
	}


	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws
		IOException {
		log.info("로그인 싪패");

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(String.valueOf(401))
			.message("잘못된 요청입니다.")
			.errorMap(new HashMap<>(Map.of("login fail", "아이디 또는 비밀번호를 확인해 주세요.")))
			.build();

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(401);
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
