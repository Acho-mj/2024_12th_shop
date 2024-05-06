package likelion12th.shop.dto;

import likelion12th.shop.constant.OrderStatus;
import likelion12th.shop.entity.Item;
import likelion12th.shop.entity.Order;
import likelion12th.shop.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderHisDto {
    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();


    private static ModelMapper modelMapper = new ModelMapper();
    public static OrderHisDto of(Order order) {
        return modelMapper.map(order,OrderHisDto.class);
    }
}
