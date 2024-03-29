package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.ItemSellStatus;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="item")
@Getter
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
}