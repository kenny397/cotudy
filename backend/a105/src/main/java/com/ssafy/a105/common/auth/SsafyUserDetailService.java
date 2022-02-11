package com.ssafy.a105.common.auth;

import com.ssafy.a105.api.service.UserService;
import com.ssafy.a105.db.entity.User;
import com.ssafy.a105.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
@RequiredArgsConstructor
public class SsafyUserDetailService implements UserDetailsService{

	private final UserRepository userRepository;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		User user = userRepository.findByUserId(username).get();
    		if(user != null) {
    			SsafyUserDetails userDetails = new SsafyUserDetails(user);
    			return userDetails;
    		}
    		return null;
    }
}
