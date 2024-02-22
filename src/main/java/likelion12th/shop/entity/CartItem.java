package likelion12th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="cart_item")
@Getter
public class CartItem extends Base {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    private Integer count;
}