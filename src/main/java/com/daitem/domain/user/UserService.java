package com.daitem.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void userAdd(UserSaveDto dto) {

        User user = User.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);
    }
}
