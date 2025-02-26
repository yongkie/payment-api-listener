package com.alphabet.simple.payment;

import com.alphabet.simple.payment.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentApiListenerApplication implements WebMvcConfigurer {
	private final TokenInterceptor tokenInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(PaymentApiListenerApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor).excludePathPatterns("/tes","/swagger-ui.html");
	}
}
