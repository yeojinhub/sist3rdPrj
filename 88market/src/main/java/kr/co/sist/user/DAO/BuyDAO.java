package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.ProductDTO;

@Mapper
public interface BuyDAO {
    public ProductDTO selectProduct(String prdNum);
    public ImageDTO selectImage(int imgNum);
    public List<AddressDTO> selectAddressByUserNum(String userNum);
    public void updateProductAppointType(String prdNum);
}
