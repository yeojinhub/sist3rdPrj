package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.UserDTO;

@Mapper
public interface UserDAO {
	  
	  /** 
     * 유저번호로 사용자 정보 전체 조회 
     * @param userNum users.USER_NUM
     * @return UserDTO
     */
	// 유저번호 
    UserDTO selectUserInfoByUserNum(String userNum);

    /**
     * 이메일로 사용자 정보 전체 조회
     * @param email users.EMAIL
     * @return UserDTO
     */
    UserDTO selectUserInfoByEmail(String email);

    /**
     * 사용자 누적 신고 수 조회
     * @param userNum users.USER_NUM
     * @return 신고 횟수 (없으면 0)
     */
    Integer selectTotalReportByUserNum(String userNum);
    
    /** 마이페이지 정보 수정 */
    public int updateUserInfo(UserDTO dto);
    
    public void withdrawUser(String userNum);

}
