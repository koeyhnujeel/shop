package zunza.myshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import zunza.myshop.config.handler.Http401Handler;
import zunza.myshop.config.handler.Http403Handler;
import zunza.myshop.jwt.JWTFilter;
import zunza.myshop.jwt.JWTUtil;
import zunza.myshop.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final ObjectMapper objectMapper;
	private final JWTUtil jwtUtil;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/favicon.ico")
			.requestMatchers("/error");
	}

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)

			// 경로 인가
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll())

			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
			.addFilterAt(
				new LoginFilter(authenticationManager(authenticationConfiguration), objectMapper, jwtUtil),
				UsernamePasswordAuthenticationFilter.class
			)
			.exceptionHandling(e ->{
				e.authenticationEntryPoint(new Http401Handler(objectMapper));
				e.accessDeniedHandler(new Http403Handler(objectMapper));
			})


			// 세션 설정
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
