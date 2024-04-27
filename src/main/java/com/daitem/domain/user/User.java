package com.daitem.domain.user;

import com.daitem.domain.common.enumset.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@Accessors(chain = true)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long userId;
    private String loginId;
    private String email;
    private String password;
    private String userName;
    @Convert(converter = UserRoles.UserRolesConverter.class)
    private UserRoles userRole;
    // 04.19 추가
    private String address1;
    private String address2;
    private String address3;

    private String phone;

    //이메일 인증한 사용자 boolean true/ false
    private YN isValid;

    // 로그인 처리 시 필요함
    private LocalDateTime lastLoginAt;
}
