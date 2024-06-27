package likelion12th.shop.service;

import jakarta.persistence.EntityNotFoundException;
import likelion12th.shop.constant.OrderStatus;
import likelion12th.shop.dto.ItemFormDto;
import likelion12th.shop.dto.OrderItemDto;
import likelion12th.shop.dto.OrderReqDto;
import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.entity.Item;
import likelion12th.shop.entity.Member;
import likelion12th.shop.entity.Order;
import likelion12th.shop.entity.OrderItem;
import likelion12th.shop.exception.OutOfStockException;
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

//@Service
//@RequiredArgsConstructor
//public class OrderService {
//    private final ItemRepository itemRepository;
//
//    private final MemberRepository memberRepository;
//
//    private final OrderRepository orderRepository;
//
//    // 주문하기
//    public Long createNewOrder(OrderReqDto orderReqDto, String email) {
//        // 이메일로 회원 조회
//        Member member = memberRepository.findByEmail(email);
//        // 회원이 존재하지 않을 경우 email로 새로운 회원 생성
//        if (member == null) {
//            Member newMember = new Member();
//            newMember.setEmail(email);
//            member = memberRepository.save(newMember);
//        }
//
//        // orderReqDto에서 상품 ID를 가져온다.
//        Long itemId = orderReqDto.getItemId();
//
//        // 상품 ID로 상품 정보를 데이터베이스에서 조회
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("상품 ID 없음 : " + itemId));
//
//
//        // 주문 상품 생성(상품과 주문 개수)
//        // hint! 주문할 상품과 수량으로 OrderItem 객체를 만드는 메소드를 호출
//        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());
//
//
//        // 주문 상품 목록과 회원 정보로 주문을 생성한다.
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(orderItem);
//        // hint! 회원과 아이템 리스트로 주문 생성하는 메소드를 호출
//        Order order = Order.createOrder(member, orderItems);
//
//        // 생성된 주문을 저장
//        orderRepository.save(order);
//
//        // 생성된 주문의 ID 반환
//        return order.getId();
//    }
//
//    // 모든 주문 내역 조회
//    public List<OrderDto> getAllOrdersByUserEmail(String email) {
//        // email로 주문을 조회하여 리스트에 저장
//        List<Order> orders = orderRepository.findByMemberEmail(email);
//
//        // 저장할 리스트를 생성
//        List<OrderDto> OrderDtos = new ArrayList<>();
//
//        // email로 조회한 각 주문에 대해 OrderDto로 변환하여 리스트에 추가
//        orders.forEach(s -> OrderDtos.add(OrderDto.of(s)));
//        return OrderDtos;
//    }
//
//
//    // 주문 상세 조회
//    public OrderItemDto getOrderDetails(Long orderId, String email) {
//        // orderId로 주문 조회
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));
//
//        // 주문을 생성한 사용자인지 확인
//        if (!order.getMember().getEmail().equals(email)) {
//            throw new IllegalArgumentException("주문자만 접근 가능함");
//        }
//
//        // 주문에 속한 주문 상품들을 가져온다.
//        List<OrderItem> orderItems = order.getOrderItemList();
//        // 비어있지 않을 경우
//        if (!orderItems.isEmpty()) {
//            OrderItem orderItem = orderItems.get(0);
//
//            // OrderItemDto 객체로 변환하여 반환
//            return OrderItemDto.of(orderItem);
//        } else {
//            throw new IllegalArgumentException("주문 아이템이 없음");
//        }
//    }
//
//
//    // 주문 취소
//    public void cancelOrder(Long orderId, String email) {
//        // 주문을 orderId로 조회
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));
//
//        // 주문을 생성한 사용자인지 확인
//        if (!order.getMember().getEmail().equals(email)) {
//            throw new IllegalArgumentException("취소 권한이 없음");
//        }
//
//        // 주문 상태를 "CANCEL"로 변경
//        // 주문 취소한 수량만큼 상품의 재고를 증가
//        order.cancelOrder();
//
//        // 주문 정보 업데이트
//        orderRepository.save(order);
//    }
//
//}
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long createNewOrder(OrderReqDto orderReqDto, String email) throws Exception {
        //이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);
        //회원이 존재하지 않을 경우, email로 새로운 회원 생성
        if (member == null) {
            Member member1 = new Member();   //member 객체 생성
            member1.setEmail(email);  // 회원 별로 주문을 구분하기 위해 email 값 설정
            member = memberRepository.save(member1);  // 새로 생성한 member 객체 db에 저장
        }

        // orderReqDto에서 상품 ID를 가져온다.
        Long itemId = orderReqDto.getItemId();

        // 상품 ID로 상품 정보를 데이터베이스에서 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품 ID 없음 : " + itemId));

        //주문 상품 생성(상품과 주문 개수)
        //주문할 상품과 수량으로 orderItem 객체를 만드는 메소드를 호출.  주어진 상품과 수량을 이용하여 새로운 주문 상품 객체를 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount()); // 주문 요청 DTO(OrderReqDto)에서 가져온 수량 정보

        //주문 상품 목록과 회원정보로 주문을 생성한다.
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        //회원과 아이템 리스트로 주문 생성하는 메소드를 호출
        Order order = Order.createOrder(member, orderItems);

        //생성된 주문을 저장
        orderRepository.save(order);
        return order.getId();
    }

    //특정 이메일 주소에 해당하는 사용자의 모든 주문 내역 조회
    public List<OrderDto> getAllOrdersByUserEmail(String email) {
        // email로 주문을 조회하여 리스트에 저장
        List<Order> orders = orderRepository.findByMemberEmail(email);

        //저장할 리스트 생성
        List<OrderDto> OrderDtos = new ArrayList<>();

        //email로 조회한 각 주문에 대해 OrderDto로 변환하여 리스트에 추가
        // order 리스트에 있는 각 주문을 반복하면서 OrderDto 객체로 변환하고, 변환된 객체를 OrderDtos 리스트에 추가하는 작업
        orders.forEach(s -> OrderDtos.add(OrderDto.of(s)));
        return OrderDtos;

    }
    

    // 주문 상세 조회
    public OrderItemDto getOrderDetails(Long orderId, String email) {
        //orderId로 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        //주문을 생성한 사용자인지 확인
        //상품을 주문한 member의 email과 전달받은 매개변수 email이 동일한지 확인
        if (!order.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("주문자만 접근 가능함");
        }

        //주문에 속한 상품들을 가져온다. 주문 객체에서 주문한 상품목록 가져오기
        List<OrderItem> orderItems = order.getOrderItemList();

        // 비어있지 않을 경우
        if (!orderItems.isEmpty()) {
            OrderItem orderItem = orderItems.get(0);

            //OrderItemDto 객체로 변환하여 반환
            return OrderItemDto.of(orderItem);
        } else {
            throw new IllegalArgumentException("주문 아이템이 없음");
        }
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId, String email) {
        //주문을 orderId로 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        //주문을 생성한 사용자인지 화긴
        if (!order.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("취소 권한이 없음");
        }

        //주문 상태를 "CANCEL"로 변경
        //주문 취소한 수량만큼 상품의 재고를 증가
        order.cancelOrder();
        //주문 정보 업데이트
        orderRepository.save(order);
    }

}
