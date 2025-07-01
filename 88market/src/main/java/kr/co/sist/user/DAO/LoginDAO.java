package kr.co.sist.user.DAO;

import java.util.HashMap;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class LoginDAO {
	
	private static LoginDAO lDAO;
	
	private String configPath;
	
	private LoginDAO(String configPath) {
		org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
		this.configPath = configPath;
	} //LoginDAO
	
	/**
	 * MyBatis Framework에서 연동할 DB의 설정을 가진 경로를 상대경로로 설정
	 * 예) 상대경로 : 패키지명/설정파일명.xml 로 설정
	 * 				(day0602/kr/co/sist/dao/mybatis-config.xml)
	 * @param configPath
	 * @return mbDAO
	 */
	public static LoginDAO getInstance(String configPath) {
		if( lDAO == null ) {
			lDAO = new LoginDAO(configPath);
		} //end if
		
		return lDAO;
	} //getInstance

	public void selectProc(HashMap<String, Object> hashmap) throws PersistenceException {
		SqlSession ss = LoginDAO.getInstance("kr/co/sist/dao/mybatis-config.xml")
				.getMyBatisHandler(); // auto commit X
		
		// 2.쿼리를 실행
		ss.selectOne("kr.co.sist.day0616.procedureSelect", hashmap);
		
		// 3. Transaction 처리
		// 4. MyBatis Handler 닫기
		ss.close();
		
	} //selectProc
}
