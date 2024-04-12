package zunza.myshop.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zunza.myshop.exception.ExpiredCodeException;
import zunza.myshop.exception.InvalidCodeException;
import zunza.myshop.request.CodeRequest;
import zunza.myshop.request.EmailRequest;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

	private final RedisTemplate<String, String> redisTemplate;
	private final JavaMailSender MailSender;

	public void sendCode(EmailRequest req) {
		String email = req.getEmail();
		String code = createCode();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@zunshop.com");
		message.setTo(email);
		message.setSubject("zunShop 이메일 인증 코드입니다.");
		message.setText("인증 번호는 " + code + " 입니다.");
		MailSender.send(message);

		ValueOperations<String, String> authenticationCodes = redisTemplate.opsForValue();
		authenticationCodes.set(email, code, 5, TimeUnit.MINUTES);
	}

	private String createCode() {
		return  String.valueOf((int)((Math.random() * 899999) + 100000));
	}

	public void checkCode(CodeRequest req) {
		String email = req.getEmail();
		String code = req.getCode();

		ValueOperations<String, String> authenticationCodes = redisTemplate.opsForValue();
		String value = authenticationCodes.get(email);

		if (value == null) {
			throw new ExpiredCodeException();
		}

		if (!value.equals(code)) {
			throw new InvalidCodeException();
		}

		redisTemplate.delete(email);
	}
}
