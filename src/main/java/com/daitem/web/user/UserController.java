package com.daitem.web.user;

import com.daitem.domain.user.UserService;
import com.daitem.domain.user.dto.UserSaveDto;
import com.daitem.web.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.daitem.domain.user.dto.LoginTokenDto;
import com.daitem.domain.user.dto.UserLoginDto;
import com.daitem.web.common.EmailConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 API")
public class UserController {

    private final UserService userService;
    private final EmailConfig emailConfig;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("api/v1/login")
    @ResponseBody
    public CommonResponse<?> userAdd(@RequestBody UserSaveDto dto) {
        userService.userAdd(dto);
        return CommonResponse.ok("회원가입에 성공했습니다.");
    }

    /**
     * 레디스에 저장한 인증번호, 회원 입력 인증번호 비교 endPoint
     * @return String (인증 성공, 실패)
     */
    @PostMapping("/api/v1/user/check-verify-code")
    public CommonResponse<String> checkVerifyCode(@RequestParam("userInput") String userInput) {
        String userEmail = redisTemplate.opsForValue().get("userEmail");
        try {
            if (emailConfig.checkVerificationCode(userInput)) {
                // 인증 코드가 일치하면 사용자의 이메일 인증 상태 변경
                userService.checkVerifyCode(userEmail);
                return CommonResponse.ok("인증 성공");
            } else {
                return new CommonResponse<>(500, "인증 실패: 코드가 다릅니다.");
            }
        } catch (Exception e) {
            return new CommonResponse<>(500, "오류가 발생했습니다.");
        }
    }

    /**
     * 회원 로그인
     * @param requestDto 회원 로그인 요청 DTO
     */
    @PostMapping("/api/v1/user/login")
    public CommonResponse<LoginTokenDto> userLogin(@RequestBody UserLoginDto requestDto) {
        return CommonResponse.ok(userService.userLoginProcess(requestDto));
    }
}
