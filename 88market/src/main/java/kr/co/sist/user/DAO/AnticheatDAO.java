package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnticheatDAO {
	/**
	 * 누적 사기 예방 수
	 * @return
	 */
	public int selectTotalprevention();
	/**
	 * 오늘 조회 수
	 * @return
	 */
	public int selectTodayLookUpScam();
	/**
	 * 누적 조회 수
	 * @return
	 */
	public int selectTotalLookUpScam();
}
