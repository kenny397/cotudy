package com.ssafy.a105.api.service;

import com.ssafy.a105.api.request.RivalPostReq;
import com.ssafy.a105.api.request.UserPostReq;
import com.ssafy.a105.api.request.UserRegisterPostReq;
import com.ssafy.a105.api.response.StudyTimeRes;
import com.ssafy.a105.db.entity.Department;
import com.ssafy.a105.db.entity.Rival;
import com.ssafy.a105.db.entity.User;
import com.ssafy.a105.db.repository.DepartmentRepository;
import com.ssafy.a105.db.repository.RivalRepository;
import com.ssafy.a105.db.repository.UserCustomRepository;
import com.ssafy.a105.db.repository.UserRepository;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)

public class UserServiceImpl implements UserService{
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RivalRepository rivalRepository;
    @Autowired
    UserCustomRepository userCustomRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    @Transactional
    public User createUser(UserRegisterPostReq registerInfo) {
        User user =new User();
        user.setUserId(registerInfo.getEmail());
        user.setNickname(registerInfo.getNickName());
        user.setPassword(passwordEncoder.encode(registerInfo.getPassword()));
        Department department = departmentRepository.findById(10L).get();
        user.setDepartment(department);
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
    public User modifyUser(UserPostReq userInfo) {
        User user = userRepository.findById(userInfo.getId());
        user.setUserId(userInfo.getUserId());
        user.setNickname(userInfo.getNickName());
        user.setGoalTime(userInfo.getGoalTime());
        user.setGoal(userInfo.getGoal());
        user.setThumbnail(userInfo.getThumbnail());
        Department department = departmentRepository.findById(userInfo.getDepartmentId()).get();
        user.setDepartment(department);
        return userRepository.save(user);
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

    @Override
    public List<Rival> getRivals(long id) {
        List<Rival> rival= rivalRepository.findByUserId(id);
        if(rival.isEmpty())System.out.println("rival 비어있음");
        else System.out.println("rival" +rival.size());

        return rival;
    }

    @Override
    @Transactional
    public void registerRival(RivalPostReq rivalPostReq) {
        Rival rival = new Rival();
        User userMain = userRepository.getById(rivalPostReq.getUserId());
        User userTarget=userRepository.getById(rivalPostReq.getRivalId());
        rival.setUser(userMain);
        rival.setRival(userTarget);
        rivalRepository.save(rival);
    }

    @Override
    @Transactional
    public void deleteRival(RivalPostReq rivalPostReq) {
        User userMain = userRepository.getById(rivalPostReq.getUserId());
        User userTarget=userRepository.getById(rivalPostReq.getRivalId());
        Rival rival = rivalRepository.findByUserIdAndRivalId(userMain.getId(),userTarget.getId());
        rivalRepository.delete(rival);
    }

    @Override
    public StudyTimeRes getDayStudyTime(long id){

        return userCustomRepository.getDayStudyTimeByUser(id);
    }

    @Override
    public StudyTimeRes getTotalStudyTime(long id) {
        return userCustomRepository.getTotalStudyTimeByUser(id);
    }

    @Override
    public User getUserByUserId(String userId) {
        System.out.println("userId");
        Optional<User> user = userRepository.findByUserId(userId);
        System.out.println("aaaa");
        System.out.println("what"+user.get().getUserId());
        return user.get();
    }
}
