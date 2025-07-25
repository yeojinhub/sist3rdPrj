package kr.co.sist.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminUserProductDTO {
	
	private ProductDTO product;
	private UserDTO user;
	private CategoryDTO category;
}
