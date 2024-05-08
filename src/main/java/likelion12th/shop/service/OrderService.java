package likelion12th.shop.service;

import likelion12th.shop.constant.OrderStatus;
import likelion12th.shop.dto.ItemFormDto;
import likelion12th.shop.dto.OrderItemDto;
import likelion12th.shop.dto.OrderReqDto;
import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.entity.Item;
import likelion12th.shop.entity.Member;
import likelion12th.shop.entity.Order;
import likelion12th.shop.entity.OrderItem;
import likelion12th.shop.repository.ItemRepository;
import likelion12th.shop.repository.MemberRepository;
import likelion12th.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Transactional
    public Long createNewOrder(OrderReqDto orderReqDto, String email) {
        // 이메일로 회원 생성
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            Member newMember = new Member();
            newMember.setEmail(email);
            member = memberRepository.save(newMember);
        }

        // 주문할 상품 정보 조회
        Long itemId = orderReqDto.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 상품을 찾을 수 없습니다: " + itemId));

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());


        // 주문 생성
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        Order order = Order.createOrder(member, orderItems);

        // 생성된 주문을 저장
        orderRepository.save(order);

        // 생성된 주문의 ID 반환
        return order.getId();
    }

    // 모든 주문 내역 조회
    public List<OrderDto> getAllOrdersByUserEmail(String email) {
        List<Order> orders = orderRepository.findByMemberEmail(email);
        List<OrderDto> OrderDtos = new ArrayList<>();
        orders.forEach(s -> OrderDtos.add(OrderDto.of(s)));
        return OrderDtos;
    }

    
    // 주문 상세 조회
    public OrderItemDto getOrderDetails(Long orderId, String email) {
        // 주문을 orderId로 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // 주문을 생성한 사용자인지 확인
        if (!order.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("Only the owner of the order can view its details.");
        }

        List<OrderItem> orderItems = order.getOrderItemList();
        if (!orderItems.isEmpty()) {
            OrderItem orderItem = orderItems.get(0);
            Item item = orderItem.getItem();

            // OrderItemDto 객체로 변환하여 반환
            return OrderItemDto.of(orderItem);
        } else {
            throw new IllegalArgumentException("Order does not contain any items.");
        }
    }

}
