package likelion12th.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
// 주문을 생성할 때 필요한 정보를 담음
public class OrderDto {
    private Long itemId;
    private int count;

}
