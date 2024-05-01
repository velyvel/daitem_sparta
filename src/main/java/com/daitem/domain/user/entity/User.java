package com.daitem.domain.user.entity;

import com.daitem.domain.common.enumset.YN;
import com.daitem.domain.order.entity.Order;
import com.daitem.domain.user.enumset.UserRoles;
import com.daitem.domain.wishlist.entity.WishList;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@Accessors(chain = true)
@Table(name = "user")
public class User implements UserDetails {

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

    // wishList 만들기
    @OneToMany(mappedBy = "user")
    private List<WishList> wishLists;

    // 주문 리스트
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    // implements : UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

//    @Override
//    public String getPassword() {
//        return "";
//    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}