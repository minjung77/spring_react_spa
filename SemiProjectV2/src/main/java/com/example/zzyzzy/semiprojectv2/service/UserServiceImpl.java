package com.example.zzyzzy.semiprojectv2.service;

import com.example.zzyzzy.semiprojectv2.domain.Member;
import com.example.zzyzzy.semiprojectv2.domain.User;
import com.example.zzyzzy.semiprojectv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User newUser(User user) {

        // 아이디 중복 체크
        if (userRepository.existsByUserid(user.getUserid())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다!!");
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다!!");
        }

        return userRepository.save(user);
    }

    @Override
    public User loginUser(User user) {
        User findUser = userRepository.findByuserid(user.getUserid()).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );

        if (findUser == null || !findUser.getPasswd().equals(user.getPasswd())) {
            throw new IllegalStateException("아이디나 비밀번호가 일치하지 않습니다!!");
        }

        return findUser;
    }
}
