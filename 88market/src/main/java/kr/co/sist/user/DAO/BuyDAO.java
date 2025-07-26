package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.PaymentsDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.TradesDTO;

@Mapper
public interface BuyDAO {
    public ProductDTO selectProduct(String prdNum);
    public ImageDTO selectImage(int imgNum);
    public List<AddressDTO> selectAddressByUserNum(String userNum);
    public void updateProductAppointType(String prdNum);
    public void insertProductTrades(TradesDTO tDTO);
    public void insertProductPayments(PaymentsDTO pDTO);
    
    //민경 - 구매목록 가져오기
    public List<TradesDTO> selectPurchaseHistory(String buyerId);
}
