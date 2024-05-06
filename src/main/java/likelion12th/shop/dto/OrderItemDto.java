package likelion12th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 주문 상품 정보를 담음
public class OrderItemDto {
    private Long itemId;
    private int count;
}
