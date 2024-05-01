package com.daitem.web.common.security;

import com.daitem.domain.user.entity.User;
import com.daitem.domain.user.enumset.UserRoles;
import com.daitem.web.common.JwtConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtConfig jwtConfig;
    private final RedisTemplate<String, String> redisTemplate;


    @Bean
    public SecurityFilterChain memberSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable);
        // 쿠키나 세션에 저장하지 않고 레디스에 저장할꺼라 토큰을!
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(header -> header.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));

//        http.authenticationProvider(memberAuthenticationProvider);
        http
                .securityMatcher("/**")
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
                                .anyRequest().permitAll() // Method 에서 권한을 세부적으로 주기 위해 모든 요청을 허용
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 회원 등급 계층 설정<br />
     * 상위 등급은 하위 등급의 모든 권한을 가진다.<br />
     * ex) ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER
     *
     * @return RoleHierarchy
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        StringBuilder sb = new StringBuilder();
        for (int i = UserRoles.values().length - 1; i >= 0; i--) {
            if (UserRoles.values()[i] == UserRoles.NONE || UserRoles.values()[i - 1] == UserRoles.NONE) continue;
            if (UserRoles.values()[i] == UserRoles.ADMIN || UserRoles.values()[i - 1] == UserRoles.ADMIN) continue;

            sb.append(UserRoles.values()[i]).append(" > ").append(UserRoles.values()[i - 1]).append(" \n ");
        }

        roleHierarchy.setHierarchy(sb.toString().trim());
        return roleHierarchy;
    }

    /**
     * JWT 인증 필터<br />
     * HTTP 헤더에 JWT 토큰이 존재하는지 확인하고, 토큰이 존재하면 토큰을 검증하여 인증 정보를 설정한다.<br />
     * 토큰이 존재하지 않거나, 토큰이 유효하지 않으면 다음 필터로 넘어간다. (아이디/비밀번호 로그인)
     */
    private class JwtAuthenticationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws ServletException, IOException {

            // HTTP 헤더에서 토큰 정보가 없으면 다음 필터로
            String authorization = request.getHeader("authorization");
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // HTTP 헤더에서 토큰 정보를 추출하고 토큰을 검증
            String token = authorization.substring(7);
            Map<String, Object> payload = jwtConfig.parser(token).verify().getPayload();
            Long userId = (Long) payload.get("userId");
            String loginId = (String) payload.get("loginId");
            UserRoles role = UserRoles.ofRole((String) payload.get("role"));

            //// 레디스에서 accessToken 확인
            String storedToken = redisTemplate.opsForValue().get("auth:accessToken:" + userId);
            if (storedToken == null || !storedToken.equals(token)) {
                throw new RuntimeException("토큰이 유효하지 않습니다.");
            }

            try {
                // 인증 정보 설정
                User user = User.builder().userId(userId).loginId(loginId).userRole(role).build();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(user);

                // SecurityContext 에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new RuntimeException("토큰 검증에 실패했습니다.");
            }

            filterChain.doFilter(request, response);

        }
    }

    private static class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws ServletException, IOException {

            try {
                filterChain.doFilter(request, response);
            } catch (JwtConfig.JwtException ex) {
                response.setStatus(ex.getCode());
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write("{\"code\":%d,\"message\":\"%s\",\"scope\":\"%s\"}"
                        .formatted(ex.getCode(), ex.getMessage(), "header"));
            }
        }
    }

}
