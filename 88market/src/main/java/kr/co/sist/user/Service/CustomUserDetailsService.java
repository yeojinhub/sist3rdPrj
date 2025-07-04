package kr.co.sist.user.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.LoginDAO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginDAO loginDAO;

    // 생성자 주입
    public CustomUserDetailsService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DAO에서 사용자 조회
        UserDTO user = loginDAO.selectLoginList(username);
        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username);
        }
        // UserDTO 에서 권한 목록 등 변환
        List<GrantedAuthority> authorities = Collections.emptyList();
        // 스프링 시큐리티 User 객체 반환
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPass(),
            authorities
        );
    }
} //class
