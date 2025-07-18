package kr.co.sist.user.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.DAO.LoginDAO;
import kr.co.sist.user.DAO.SignUpDAO;

/**
 * 로그인 관련 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
public class LoginService {
    
    @Autowired
    private LoginDAO loginDAO;

    @Autowired
    private SignUpDAO signUpDAO;
    
    /**
     * 일반 로그인 처리 (이메일 + 비밀번호 검증)
     */
    public UserDTO selectLogin(String email, String pass) {
        UserDTO loginDTO = loginDAO.selectLoginList(email);
        
        // 일반 사용자: 비밀번호 검증 필요
        if( loginDTO != null && loginDTO.getPass().equals(pass) && !"kakao".equals(loginDTO.getPass()) ) {
            return loginDTO;
        }
        
        return null;
    }

    /**
     * 카카오 사용자 찾기 (비밀번호 검증 없음)
     */
    public UserDTO findKakaoUser(String kakaoEmail) {
        UserDTO user = loginDAO.selectLoginList(kakaoEmail);
        
        // 카카오 사용자 확인 (pass가 "kakao"인 경우만)
        if (user != null && "kakao".equals(user.getPass())) {
            return user;
        }
        
        return null;
    }
    
    /**
     * 카카오 사용자 생성
     */
    @Transactional
    public UserDTO createKakaoUser(UserDTO userDTO) {
        String userNum = generateUserNum();
        userDTO.setUserNum(userNum);
        
        // 1. 사용자 생성
        signUpDAO.insertKakaoUser(userDTO);
        
        // 2. 주소 테이블에도 INSERT
        signUpDAO.addKakaoAddress(userDTO);
        
        return userDTO;
    }
    
    private String generateUserNum() {
        return "USER_" + System.currentTimeMillis();
    }

    /**
     * 네이버 사용자 찾기
     */
    public UserDTO findNaverUser(String naverEmail) {
        UserDTO user = loginDAO.selectLoginList(naverEmail);
        
        if (user != null && "naver".equals(user.getPass())) {
            return user;
        }
        
        return null;
    }
    
    /**
     * 네이버 사용자 생성
     */
    @Transactional
    public UserDTO createNaverUser(UserDTO userDTO) {
        String userNum = generateUserNum();
        userDTO.setUserNum(userNum);
        
        // 사용자 생성
        signUpDAO.insertNaverUser(userDTO);
        
        // 주소 테이블에도 INSERT
        signUpDAO.addNaverAddress(userDTO);
        
        return userDTO;
    }
}
