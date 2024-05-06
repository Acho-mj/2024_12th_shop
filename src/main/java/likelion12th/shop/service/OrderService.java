package likelion12th.shop.service;

import jakarta.persistence.EntityNotFoundException;
import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.dto.OrderHisDto;
import likelion12th.shop.dto.OrderItemDto;
import likelion12th.shop.entity.Item;
import likelion12th.shop.entity.Member;
import likelion12th.shop.entity.Order;
import likelion12th.shop.entity.OrderItem;
import likelion12th.shop.repository.ItemRepository;
import likelion12th.shop.repository.MemberRepository;
import likelion12th.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    // 주문하기
    public Long order(OrderDto orderDto, String email){

        // 현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회한다.
        Member member = memberRepository.findByEmail(email);

        // 주문할 상품 리스트를 담을 ArrayList 생성
        List<OrderItem> orderItemList = new ArrayList<>();

        // 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성한다.
        // orderItems는 주문할 상품들을 담고 있는 리스트
        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            // 주문할 상품을 조회하기 위해 주어진 orderItemDto의 itemId를 사용
            // itemRepository에서 해당 상품을 찾는다.
            Item item = itemRepository.findById(orderItemDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            // 조회한 상품 정보와 주문 수량을 사용하여 주문 상품 엔티티를 생성
            OrderItem orderItem = OrderItem.createOrderItem(item, orderItemDto.getCount());
            orderItemList.add(orderItem);
        }

        // 회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성한다.
        Order order = Order.createOrder(member, orderItemList);

        // 생성한 주문 엔티티를 저장한다.
        orderRepository.save(order);

        return order.getId();
    }

    // 모든 주문 내역 조회
    public List<OrderHisDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderHisDto::of)
                .collect(Collectors.toList());
    }
}
