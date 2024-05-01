package com.daitem.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTokenDto {

    private String accessToken;
    private String refreshToken;

}
