package com.example.demo9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*
    http.formLogin(Customizer.withDefaults()) // 사용자 지정 로그인 폼(Customizer는 기본 로그인 폼 우회용으로 사용).
            .logout(Customizer.withDefaults());
    */
    CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
    requestHandler.setCsrfRequestAttributeName("_csrf");

    // 사용자가 만든 로그인폼에 대해서만 허용처리.
    http.csrf(csrf -> csrf
            .csrfTokenRequestHandler(requestHandler)
            .ignoringRequestMatchers("/ckeditor/imageUpload")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .formLogin(form -> form
            .loginPage("/member/memberLogin")
            .defaultSuccessUrl("/member/memberLoginOk", true)
            .failureUrl("/member/login/error")
            .usernameParameter("email")   // 기본은 username인데, 테이블에서 email을 아이디로 사용하니 지정해준다.
            .permitAll());

    // 각 페이지에 대한 접근 권한설정.
    http.authorizeHttpRequests(request -> request
            .requestMatchers("/images/**", "/message/**", "/ckeditorUpload/**").permitAll()
            .requestMatchers("/", "/css/**", "/js/**", "/guest/**").permitAll()
            .requestMatchers("/member/memberJoin").permitAll()
            .requestMatchers("/member/memberLoginOk","/member/memberLogout","/member/memberMain").authenticated()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
    );

    // 권한 없는 user의 접근시 예외처리.
    http.exceptionHandling(exception -> exception
            .accessDeniedPage("/error/accessDenied"));

    // ckeditor 용 경로 허용.
    http.headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()));

    // 기본 로그아웃 처리.
    http.logout(Customizer.withDefaults());


    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
