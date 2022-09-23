package server.knucd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import server.knucd.exception.handler.TokenAccessDeniedHandler;
import server.knucd.exception.handler.TokenNotAuthenticatedHandler;
import server.knucd.security.filter.TokenAuthenticationFilter;
import server.knucd.security.filter.TokenExceptionFilter;
import server.knucd.security.provider.TokenProvider;
import server.knucd.utils.redis.RedisUtil;

import javax.servlet.FilterChain;

/**
 * 서버 Spring Security FilterChain 설정
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    TokenAuthenticationFilter tokenAuthenticationFilter(TokenProvider tokenProvider, RedisUtil redisUtil) {
        return new TokenAuthenticationFilter(tokenProvider, redisUtil);
    }

    TokenExceptionFilter tokenExceptionFilter(ObjectMapper objectMapper) {
        return new TokenExceptionFilter(objectMapper);
    }

    @Bean
    SecurityFilterChain tokenFilterChain(TokenProvider tokenProvider,
                                 HttpSecurity http, ObjectMapper objectMapper,
                                         RedisUtil redisUtil) throws Exception {
        return setTokenHttpSecurity(http, objectMapper)
                .requestMatchers()
                .antMatchers(HttpMethod.POST, "/api/v1/**")
                .antMatchers(HttpMethod.DELETE, "/api/v1/**")
                .antMatchers("/api/v1/**/me")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/**").hasRole("USER")
                .and()
                .addFilterAfter(tokenAuthenticationFilter(tokenProvider, redisUtil),
                        TokenExceptionFilter.class)
                .build();
    }

    private HttpSecurity setTokenHttpSecurity(HttpSecurity http, ObjectMapper objectMapper) throws Exception{
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new TokenNotAuthenticatedHandler(new ObjectMapper()))
                .accessDeniedHandler(new TokenAccessDeniedHandler(new ObjectMapper()))
                .and()
                .addFilterBefore(tokenExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter.class);
    }
}
