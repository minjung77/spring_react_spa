package com.example.zzyzzy.semiprojectv2.config;

import com.example.zzyzzy.semiprojectv2.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
//    private final UserDetailsService userDetailService;
//    private final CustomAuthenticationSuccessHandler successHandler;
//    private final CustomAuthenticationFailureHandler failureHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//CSRF 필터를 끔
//                    .userDetailsService(userDetailService)
                    .authorizeRequests()//url 기반 인가 설정
                    .antMatchers("/", "/api/auth/**", "/gallery/**", "/api/board/**", "/api/member/**").permitAll()// 인증/인가 여부와 상관없이 접근 가능
                .and()
//                .formLogin()
//                    .loginPage("/member/login") //커스텀 로그인 페이지 설정
//                    .usernameParameter("userid") //아이디 매개변수 지정 !!
//                    .passwordParameter("passwd") //비밀번호 매개변수 지정 !!
//                    .defaultSuccessUrl("/member/myinfo")//로그인 성공 시 리다이텍트 url
//                    .failureUrl("/member/loginfail")//로그인 실패시 리다이렉트 url
//                    .successHandler(successHandler)
//                    .failureHandler(failureHandler)
//                    .permitAll()
//                .and()
//                .logout()// 로그아웃 설정
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
//                    .logoutSuccessUrl("/")// 로그아웃 성공 후 리다이렉트될 url
//                    .invalidateHttpSession(true)// 세션 무효화
//                    .deleteCookies("JSESSIONED")//JSESSIONID 쿠키 삭제
//                .permitAll();

                //jwt 인증으로 변경
                .sessionManagement()//jwt인증을 위해 stateless 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //jwt 필터 설정
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
