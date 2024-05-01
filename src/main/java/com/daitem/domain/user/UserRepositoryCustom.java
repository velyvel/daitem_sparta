package com.daitem.domain.user;

import com.daitem.domain.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepositoryCustom {
    UserDto searchUserBy(Long userId);
}
