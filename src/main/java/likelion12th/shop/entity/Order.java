package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // mysql 예약어 order
@Getter @Setter
public class Order extends Base {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItemList = new ArrayList<>();

    // 주문 시간
    private LocalDateTime orderDate;

    // orderItemList에 주문 상품 정보를 담는다.
    public void addOrderItem(OrderItem orderItem) {
        // orderItem 객체를 order 객체의 orderItemList에 추가한다.
        orderItemList.add(orderItem);
        // Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계이므로
        // orderItem 객체에도 order 객체를 세팅한다.
        orderItem.setOrder(this);
    }

    // 주문 생성하기
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        // 상품을 주문한 회원의 정보를 세팅한다.
        order.setMember(member);

        // 장바구니 페이지에서는 한 번에 여러 개의 상품을 주문할 수 있으므로
        // 여러 개의 주문 상품을 담을 수 있도록 리스트 형태로 주문 객체에 orderItem 객체를 추가한다.
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        // 주문 상태를 ORDER로 세팅한다.
        order.setOrderStatus(OrderStatus.ORDER);
        // 현재 시간을 주문 시간으로 세팅한다.
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 총 주문 금액을 구하는 메소드
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}