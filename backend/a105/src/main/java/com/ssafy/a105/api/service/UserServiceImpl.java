package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.db.entity.User;
import com.ssafy.a105.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public User createUser(UserRegisterPostReq registerInfo) {
        User user =new User();
        user.setUserId(registerInfo.getEmail());
        user.setNickname(registerInfo.getNickName());
        user.setPassword(registerInfo.getEmail());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.getById(userId);
        userRepository.delete(user);
    }

    @Override
    public User modifyUser(UserRegisterPostReq registerInfo) {
        return null;
    }

    @Override
    public User getUserInfo(long userId) {
        return null;
    }

    @Override
    public long getCheckEmail(String email) {

        return 0;
    }

    @Override
    public long getCheckNickName(String nickName) {
        return 0;
    }
}
