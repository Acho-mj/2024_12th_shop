package likelion12th.shop.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {     // 장바구니 주문
    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;
}
