package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.ItemSellStatus;
import likelion12th.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="item")
@Getter @Setter
@ToString
public class Item extends Base {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer price;
    private Integer stock;
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private String itemImg;
    private String itemImgPath;

    // 상품의 재고를 감소시키는 로직
    public void removeStock(int stockNumber){
        // 상품의 재고 수량에서 주문 후 남은 재고 수량을 구한다.
        int restStock = this.stock - stock;
        if(restStock<0){
            // "상품의 재고 < 주문 수량"일 경우 재고 부족 예외 발생
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stock + ")");
        }
        // 주문 후 남은 재고 수량을 상품의 현재 재고 값으로 할당한다.
        this.stock = restStock;
    }
}
