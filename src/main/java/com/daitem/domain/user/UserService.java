package com.daitem.domain.user;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.user.dto.LoginTokenDto;
import com.daitem.domain.user.dto.UserLoginDto;
import com.daitem.domain.user.dto.UserSaveDto;
import com.daitem.domain.user.entity.User;
import com.daitem.domain.user.enumset.UserRoles;
import com.daitem.web.common.EmailConfig;
import com.daitem.web.common.JwtConfig;
import com.daitem.web.common.security.MemberAuthenticationProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfig emailConfig;

    private final MemberAuthenticationProvider memberAuthenticationProvider;
    private final JwtConfig jwtConfig;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 회원가입 로직
     * 1. 필수 정보 입력(아이디, 비밀번호, 이메일, 사용자 이름 주소) 후 회원가입 하면 userRole : 'NONE' 으로 저장
     *  - validation : 중복 아이디, 아이디 형식, 중복 이메일, 이메일 형식, 비밀번호 영문,특수문자 체크/ 암호화 : ,
     * 2. 회원 가입한 정보로 이메일 날아감, 레디스 캐시서버 사용하여 일정 시간 안에 인증할 수 있도록 구현
     * */

    @Transactional
    public void userAdd(UserSaveDto dto) {
        /**
         * 각각 validation 화요일에 작성한다.
         * */
        String encodePassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .loginId(dto.getLoginId())
                .password(encodePassword)
                .userName(dto.getUserName())
                .userRole(UserRoles.NONE)
                .email(dto.getEmail())
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .address3(dto.getAddress3())
                .isValid(YN.N)
                .build();
        userRepository.save(user);

        // 이메일 인증 시 질문 ?
        String to = dto.getEmail();
        if (to != null) {
            emailConfig.sendMail(to);
        } else {
            // 이메일이 null인 경우 처리
            System.out.println("이메일이 없습니다.");
        }
    }

    public void checkVerifyCode(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.setUserRole(UserRoles.NORMAL);
        user.setIsValid(YN.Y);
    }

    @Transactional
    public LoginTokenDto userLoginProcess(UserLoginDto requestDto) {

        if (!hasText(requestDto.getLoginId()) || !hasText(requestDto.getPassword())) {
            throw new RuntimeException("로그인 정보를 입력해주세요.");
        }

        try {
            Authentication authentication = memberAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getLoginId(), requestDto.getPassword()));

            User user = (User) authentication.getPrincipal();
            user.setLastLoginAt(LocalDateTime.now()); // 마지막 로그인 시간 업데이트
            userRepository.save(user); // DB에 사용자 정보 업데이트

            String accessToken = jwtConfig.builder()
                    .claim("userId", user.getUserId())
                    .claim("loginId", user.getLoginId())
                    .claim("role", user.getUserRole().getRole())
                    .expirationTime(Duration.ofHours(1).toSeconds())
                    .build();

            String refreshToken = jwtConfig.builder()
                    .claim("userId", user.getUserId())
                    .claim("loginId", user.getLoginId())
                    .claim("role", user.getUserRole().getRole())
                    .expirationTime(Duration.ofDays(7).toMillis())
                    .build();

            // Redis에 accessToken과 refreshToken 저장
            redisTemplate.opsForValue().set("auth:accessToken:" + user.getUserId(), accessToken, Duration.ofHours(1));
            redisTemplate.opsForValue().set("auth:refreshToken:" + user.getUserId(), refreshToken, Duration.ofDays(7));

            LoginTokenDto loginTokenDto = new LoginTokenDto();
            loginTokenDto.setAccessToken(accessToken);
            loginTokenDto.setRefreshToken(refreshToken);

            return loginTokenDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("토큰 생성 중 오류가 발생했습니다.");
        }
    }
}
