package com.example.login.config;

import com.example.login.config.handler.AutoFailHandler;
import com.example.login.controller.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AutoFailHandler autoFailHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 요청이 들어왔을때 처리해줄지에 대한 메소드
        http.authorizeHttpRequests(auth -> {   // 서버의 리소스에 접근 가능한 권한을 설정함.
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/").permitAll();  // 이 요청은 모든 사용자에게 허락한다. (인증없이도)
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());  // Role_admin 이라는 권한이 아니면 다 거절한다.
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());    // user 에게만 ( 판매자, 구매자 구분 할 때 등에 사용)
            auth.anyRequest().authenticated();  // 모든 요청 - 인증된 사용자에게 허용해 주겠다.
        }).formLogin(login ->{
            login.loginPage("/auth/login"); // 로그인 페이지에 해당되는 서블릿이 존재해야 한다. get으로 요청 시 만든 서블릿 동작, post 동작 시 스프링시큐리티에서 알아서 처리
            login.usernameParameter("user");
            login.passwordParameter("pass");   // html 인풋 네임 값 조절
            login.defaultSuccessUrl("/"); //로그인에 성공했을 때 어디로 보낼지 설정 - 서블릿 존재해야 함
            login.failureHandler(autoFailHandler);  //로그인 실패 시 처리
        }).logout(logout ->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));  // 로그아웃 요청 시 날릴 url 설정  post 로 요청을 날리면 페이지 만들 필요 없음
            logout.deleteCookies("JSESSIONID");  // 사용자가 세션을 쓰지 못하게 제거 logout 요청 시
            logout.invalidateHttpSession(true); // 세션이 소멸하도록 허용하는 메소드
            logout.logoutSuccessUrl("/");  // 로그아웃 완료 후 이동할 페이지 설정
        }).sessionManagement(session ->{  // 세션을 컨트롤
            session.maximumSessions(1); // 세션의 갯수 제한  1로 설정 시 중복 로그인 허용 X
            session.invalidSessionUrl("/");   // 세션 만료시 이동할 페이지
        }).csrf(csrf -> csrf.disable());  // csrf 공격 방지

        return http.build();
    }


}
