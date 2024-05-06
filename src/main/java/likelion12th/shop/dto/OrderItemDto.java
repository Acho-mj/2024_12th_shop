package likelion12th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 주문 상품 정보를 담음
public class OrderItemDto {
    private Long itemId;
    private String itemNm; // 상품명
    private int count; // 주문 수량
    private int orderPrice;
    private String itemImgPath;

    public Long getItemId() {
        return itemId;
    }
}
