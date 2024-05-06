package likelion12th.shop.service;

import jakarta.persistence.EntityNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    // 주문하기
    public Long order(OrderDto orderDto, String email){

        // 주문할 상품을 조회한다.
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회한다.
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        // 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성한다.
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        // 회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성한다.
        Order order = Order.createOrder(member, orderItemList);
        // 생성한 주문 엔티티를 저장한다.
        orderRepository.save(order);

        return order.getId();
    }
}
