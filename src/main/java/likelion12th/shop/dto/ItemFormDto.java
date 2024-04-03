package likelion12th.shop.dto;

import likelion12th.shop.constant.ItemSellStatus;
import likelion12th.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    private  Long id;
    @NotNull
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private String itemDetail;
    @NotNull
    private Integer stock;
    @NotNull
    private ItemSellStatus itemSellStatus;
    private static ModelMapper modelMapper = new ModelMapper();

    // get에서만 사용
    private String itemImgPath;

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item,ItemFormDto.class);
    }


}
