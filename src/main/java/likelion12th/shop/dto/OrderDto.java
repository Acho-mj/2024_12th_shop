package likelion12th.shop.dto;

import likelion12th.shop.constant.OrderStatus;
import likelion12th.shop.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태
    private Long itemId;
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();
    
    // Order 객체를 OrderDto로 변환
    public static OrderDto of(Order order){
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        if (!order.getOrderItemList().isEmpty()) {
            orderDto.setItemId(order.getOrderItemList().get(0).getItem().getId());
        }
        return orderDto;
    }

}
