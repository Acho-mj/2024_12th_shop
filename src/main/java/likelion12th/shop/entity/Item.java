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
    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stock + ")");
        }
        this.stock = restStock;
    }
}
