package likelion12th.shop.dto;

import likelion12th.shop.constant.OrderStatus;
import likelion12th.shop.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태
    private List<Long> itemIds; // 여러 아이템 ID
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();
    
    // Order 객체를 OrderDto로 변환
    public static OrderDto of(Order order){
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setItemIds(order.getOrderItemList().stream()
                .map(orderItem -> orderItem.getItem().getId())
                .collect(Collectors.toList()));
        return orderDto;
    }

}
