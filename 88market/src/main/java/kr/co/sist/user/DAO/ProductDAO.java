package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface ProductDAO {
    public void insertProduct(ProductDTO product);
    public int getNextProductSeq();
}
