package likelion12th.shop.controller;

import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.dto.OrderHisDto;
import likelion12th.shop.entity.Order;
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
    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity order(@RequestBody OrderDto orderDto){

        String email = "a@naver.com";
        Long orderId;

        try {
            // 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출한다.
            orderId = orderService.order(orderDto, email);
        } catch(Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }

        // 생성된 주문 번호 반환
        return ResponseEntity.ok().body(orderId);
    }

    // 주문 내역 전체 조회
    @GetMapping("/his/all")
    public ResponseEntity<List<OrderHisDto>> getOrders() {
        List<OrderHisDto> orderHistory = orderService.getOrders();
        return ResponseEntity.ok().body(orderHistory);
    }
}
