package com.daitem.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class UserLoginDto {
    @Schema(description = "로그인 ID")
    private String loginId;

    @Schema(description = "비밀번호")
    private String password;

}
