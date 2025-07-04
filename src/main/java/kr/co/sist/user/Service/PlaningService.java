package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.user.DAO.PlaningDAO;

@Service
public class PlaningService {

	@Autowired
	private PlaningDAO planingDAO;
	
	public List<CompanyWithProductDTO> getCompanyNames(){
		return planingDAO.selectPlaningList();
	}
	
	public List<ProductDTO> getProductListByComNum(String comNum) {
        return planingDAO.selectProductsByComNum(comNum);
    }
	
}
