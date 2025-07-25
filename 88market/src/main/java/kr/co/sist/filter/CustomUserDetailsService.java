package kr.co.sist.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private CustomUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userNum) throws UsernameNotFoundException {
        UserDTO user = userMapper.selectUserByUserNum(userNum);
        if (user == null) {
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }

        return User.builder()
        .username(user.getUserNum())
        .password("")
        .authorities("ROLE_USER")
        .build();
    }
}
