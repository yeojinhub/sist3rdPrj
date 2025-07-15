package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface BuyDAO {
    public ProductDTO selectProduct(String prdNum);
    public ImageDTO selectImage(int imgNum);
}
