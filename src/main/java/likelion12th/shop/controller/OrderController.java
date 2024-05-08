package likelion12th.shop.controller;

import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.dto.OrderItemDto;
import likelion12th.shop.dto.OrderReqDto;
import likelion12th.shop.entity.Order;
import likelion12th.shop.entity.OrderItem;
import likelion12th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    // 주문하기
    @PostMapping("/new")
    public ResponseEntity<String> createNewOrder(@RequestBody OrderReqDto orderReqDto, @RequestParam String email) {
        try {
            // email로 받아온 사용자 이름으로 주문을 생성
            Long orderId = orderService.createNewOrder(orderReqDto, email);

            return ResponseEntity.ok("사용자: " + email + ", 주문 ID: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패 : " + e.getMessage());
        }
    }

    // 주문 내역 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserEmail(@RequestParam String email) {
        List<OrderDto> orders = orderService.getAllOrdersByUserEmail(email);
        return ResponseEntity.ok(orders);
    }

    
    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderItemDto> getOrderDetails(@PathVariable Long orderId, @RequestParam String email) {
        OrderItemDto orderItemDto = orderService.getOrderDetails(orderId, email);
        return ResponseEntity.ok(orderItemDto);
    }


}
