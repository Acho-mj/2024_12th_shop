package likelion12th.shop.controller;

import likelion12th.shop.dto.ItemFormDto;
import likelion12th.shop.entity.Item;
import likelion12th.shop.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/new")
    public ResponseEntity<Long> createItem(@RequestPart(name = "itemFormDto") ItemFormDto itemFormDto,
                                           @RequestPart(value = "itemImg", required = false) MultipartFile itemImg) {
        if (itemImg == null) {
            try {
                // 새로운 아이템 저장
                Long itemId = itemService.saveItem(itemFormDto);

                // 저장된 아이템의 아이디 반환
                return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
            } catch (Exception e) {
                // 예외 발생 시 처리
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            try {
                // 새로운 아이템 저장
                Long itemId = itemService.saveItem(itemFormDto, itemImg);

                // 저장된 아이템의 아이디 반환
                return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
            } catch (Exception e) {
                // 예외 발생 시 처리
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ItemFormDto>> getItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

}
