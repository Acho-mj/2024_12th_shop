package likelion12th.shop.controller;

import likelion12th.shop.dto.OrderDto;
import likelion12th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    // 주문하기
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody OrderDto orderDto){

        String email = "a@naver.com";
        Long orderId;

        try {
            // 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출한다.
            orderId = orderService.order(orderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 생성된 주문 번호와 요청 성공 HTTP 응답 상태 코드를 반환
        return new ResponseEntity<Long>(orderId , HttpStatus.OK);
    }

}
