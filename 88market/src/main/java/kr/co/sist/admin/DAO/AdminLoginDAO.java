package kr.co.sist.admin.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.DTO.CompanyDTO;

@Mapper
public interface AdminLoginDAO {
    /**
     * 관리자 ID와 비밀번호로 관리자 정보를 조회합니다.
     * @param id 관리자 ID
     * @param pass 관리자 비밀번호
     * @return 조회된 AdminDTO 객체 또는 null
     */
    public AdminDTO selectAdminByIdAndPass(@Param("id") String id, @Param("pass") String pass);

    /**
     * 기업 ID와 비밀번호로 기업 정보를 조회합니다.
     * @param id 기업 ID
     * @param pass 기업 비밀번호
     * @return 조회된 CompanyDTO 객체 또는 null
     */
    public CompanyDTO selectCompanyByIdAndPass(@Param("id") String id, @Param("pass") String pass);
}
