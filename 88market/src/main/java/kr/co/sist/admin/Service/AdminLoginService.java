package kr.co.sist.admin.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.AdminDTO;
import kr.co.sist.DTO.CompanyDTO;
import kr.co.sist.admin.DAO.AdminLoginDAO;

@Service
public class AdminLoginService {

    @Autowired
    private AdminLoginDAO adminLoginDAO;

    /**
     * 관리자 계정으로 로그인 시도
     * @param id 관리자 ID
     * @param pass 관리자 비밀번호
     * @return 로그인 성공 시 AdminDTO, 실패 시 null
     */
    public AdminDTO loginAdmin(String id, String pass) {
        return adminLoginDAO.selectAdminByIdAndPass(id, pass);
    }

    /**
     * 기업 계정으로 로그인 시도
     * @param id 기업 ID
     * @param pass 기업 비밀번호
     * @return 로그인 성공 시 CompanyDTO, 실패 시 null
     */
    public CompanyDTO loginCompany(String id, String pass) {
        return adminLoginDAO.selectCompanyByIdAndPass(id, pass);
    }
}
