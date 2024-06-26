package zunza.myshop.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private SecretKey secretKey;

	public JWTUtil(
		@Value("${spring.jwt.secret}")String secret) {

		secretKey = new SecretKeySpec(
			secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm()
		);
	}

	public Long getUserId(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("userId", Long.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	public String createJwt(Long userId, String role, Long expiredMs) {
		Date nowInKST = new Date(System.currentTimeMillis() + TimeZone.getTimeZone("Asia/Seoul").getOffset(System.currentTimeMillis()));

		return Jwts.builder()
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(nowInKST)
			.expiration(new Date(nowInKST.getTime() + expiredMs))
			.signWith(secretKey)
			.compact();
	}
}
