package likelion12th.shop.entity;
import jakarta.persistence.*;
import likelion12th.shop.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="item")
@Getter
@Setter
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


}