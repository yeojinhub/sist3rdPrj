package kr.co.sist.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
	
	private ProductDTO productDTO;
	private TradesDTO tradesDTO;
	private PaymentsDTO paymentDTO;
	
}
