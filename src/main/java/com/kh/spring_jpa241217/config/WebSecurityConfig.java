package com.kh.spring_jpa241217.config;

import com.kh.spring_jpa241217.jwt.JwtAccessDeniedHandler;
import com.kh.spring_jpa241217.jwt.JwtAuthenticationEntryPoint;
import com.kh.spring_jpa241217.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 인증 실패 시 처리할 클래스
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler; // 인가 실패 시 처리할 클래스
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 객체를 Bean으로 등록
    }

    @Bean // SecurityFilterChain 객체를 Bean으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // httpBasic 설정
        http.httpBasic(Customizer.withDefaults());

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 정책: STATELESS
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 예외 처리
        http.exceptionHandling(exceptions ->
                exceptions
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
        );

        // 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("api/auth/**", "/ws/**", "api/members/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",  // 필요 시
                        "/webjars/**",            // 필요 시
                        "/sign-api/exception"     // 예외로 둘 경로가 있다면 추가
                ).permitAll()
                .anyRequest().authenticated()
        );

        // JWT 관련 설정 적용
        http.apply(new JwtSecurityConfig(tokenProvider));

        // CORS 설정
        http.cors(Customizer.withDefaults());

        return http.build();
    }
    @Override  // 메소드 오버라이딩, localhost:3000 번으로 들어오는 요청 허가
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}