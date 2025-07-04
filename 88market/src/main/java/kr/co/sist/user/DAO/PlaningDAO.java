package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.sist.DTO.CompanyWithProductDTO;
import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface PlaningDAO {

	List<CompanyWithProductDTO> selectPlaningList();
	List<ProductDTO> selectProductsByComNum(@Param("comNum") String comNum);
	
}
