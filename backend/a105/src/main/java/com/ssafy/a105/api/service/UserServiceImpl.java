package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.db.entity.User;
import com.ssafy.a105.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    @Transactional
    public User createUser(UserRegisterPostReq registerInfo) {
        User user =new User();
        user.setUserId(registerInfo.getEmail());
        user.setNickname(registerInfo.getNickName());
        user.setPassword(registerInfo.getPassword());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = userRepository.getById(userId);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User modifyUser(UserRegisterPostReq registerInfo) {
        return null;
    }

    @Override
    public User getUserInfo(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public long getCheckEmail(String email) {
        Optional<User> user = userRepository.findByUserId(email);
        if(!user.isPresent())return 0;
        return 1;
    }

    @Override
    public long getCheckNickName(String nickName) {
        Optional<User> user = userRepository.findByNickname(nickName);
        if(!user.isPresent())return 0;
        return 1;
    }
}
