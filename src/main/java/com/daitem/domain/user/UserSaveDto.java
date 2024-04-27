package com.daitem.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDto {
    private String loginId;
    private String password;
    private String userName;
    private String email;
}
