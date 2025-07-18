package kr.co.sist.user.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sist.DTO.AddressDTO;
import kr.co.sist.DTO.ImageDTO;
import kr.co.sist.DTO.PaymentsDTO;
import kr.co.sist.DTO.ProductDTO;
import kr.co.sist.DTO.TradesDTO;
import kr.co.sist.user.DAO.BuyDAO;

@Service
public class BuyService {
    
    @Autowired
    private BuyDAO buyDAO;

    public ProductDTO searchProduct(String prdNum) {
        return buyDAO.selectProduct(prdNum);
    }// searchProduct

    public ImageDTO searchImage(int imgNum) {
        return buyDAO.selectImage(imgNum);
    }// searchImage
    
    public List<AddressDTO> searchAddressByUserNum(String userNum) {
    	return buyDAO.selectAddressByUserNum(userNum);
    }// searchAddressByUserNum
    
    /**
     * 구매자가 결제 페이지에서 결제를 완료 했을경우 거래(TRADE), 결제(PAYMENTS) 테이블에 INSERT한다.
     * @param data
     */
    @Transactional
    public void addTradeHistroy(Map<String, Object> data, UserDetails user) {
    	
    	// 1. Map으로 이루어진 data, DTO로 쪼개기
    	// 1-1. 거래(TRADE) 테이블에 넣기 위한 DTO 생성
    	TradesDTO tDTO = new TradesDTO();
    	tDTO.setPrdNum((String) data.get("prdNum")); // 상품번호
    	tDTO.setSellerId((String) data.get("userNum")); // 판매자 번호
    	tDTO.setBuyerId(user.getUsername()); // 구매자번호
    	tDTO.setDeliveryNum((String) data.get("addrNum")); // 배송지번호
    	tDTO.setTradeStatus("거래진행중");
    	
    	// 1-2. 결제(PAYMENTS) 테이블에 넣기 위한 DTO 생성
    	PaymentsDTO pDTO = new PaymentsDTO();
    	pDTO.setPatmentUid((String) data.get("impUid"));
    	pDTO.setMethod((String) data.get("payMethod"));
    	pDTO.setAmount((int) Double.parseDouble((String) data.get("price")));
    	pDTO.setCardCompany((String) data.get("cardName"));

    	// 2. 쿼리문 진행
    	// 2-1. 상품(PRODUCT) 테이블에 APPOINT_TYPE을 Y로 변경한다.
    	buyDAO.updateProductAppointType((String) data.get("prdNum"));
    	
    	// 2-2. 거래(TRADE) 테이블에 INSERT
    	
    	
    	// 2-3. 결제(PAYMENTS) 테이블에 INSERT
    	
    	
    }// addTradeHistory
    
}// class
