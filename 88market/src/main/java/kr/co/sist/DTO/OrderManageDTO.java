package kr.co.sist.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderManageDTO {

	private String orderNumber;      // 결제번호 (payments.payment_uid)
    private String productName;      // 상품명 (product.title)
    private String brandName;        // 브랜드명 (company.com_name)
    private String buyerName;        // 구매자 이름 (users.name)
    private String tradeStatus;      // 거래상태 (trades.trade_status)
    private Date orderDate;          // 주문일 (trades.trade_date)
    private String buyerAddress;     // 구매자 주소 (users.address)
    private String buyerTel;         // 구매자 전화번호 (users.tel)
    private int quantity;            // 상품 수량 (product.prd_cnt)
    private String paymentMethod;    // 결제수단 (payments.method)
    private int paymentAmount;       // 결제금액 (payments.amount)
    private String tradeId;			 // 거래번호 
	
}
