package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.ScamDTO;

@Mapper
public interface AnticheatDAO {
	/**
	 * 누적 사기 예방 수
	 * 
	 * @return
	 */
	public int selectTotalprevention();

	/**
	 * 오늘 조회 수
	 * 
	 * @return
	 */
	public int selectTodayLookUpScam();

	/**
	 * 누적 조회 수
	 * 
	 * @return
	 */
	public int selectTotalLookUpScam();

	/**
	 * 실시간 사기 모니터링 현황 조회
	 * 
	 * @return
	 */
	public List<ScamDTO> selectRealTimeMonitoring();

	/**
	 * 사기 조회 기록 추가
	 * 
	 * @param inputValue
	 */
	public void insertScam(String inputValue);

	/**
	 * 사용자 정보로 사기 조회 결과 조회
	 * 
	 * @param inputValue
	 * @return
	 */
	public int selectScamResultWithUsers(String inputValue);

	/**
	 * 계좌 정보로 사기 조회 결과 조회
	 * 
	 * @param inputValue
	 * @return
	 */
	public int selectScamResultWithBank(String inputValue);
}
