package com.daitem.web.user;

import com.daitem.domain.user.UserSaveDto;
import com.daitem.domain.user.UserService;
import com.daitem.web.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 API")
public class UserController {

    private final UserService userService;

    @PostMapping("api/v1/login")
    @ResponseBody
    public CommonResponse<?> userAdd(@RequestBody UserSaveDto dto) {
        userService.userAdd(dto);
        return CommonResponse.ok("회원가입에 성공했습니다.");
    }
}
