package likelion12th.shop.service;

import likelion12th.shop.dto.ItemFormDto;
import likelion12th.shop.entity.Item;
import likelion12th.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long saveItem(ItemFormDto itemFormDto) throws Exception{
        // 상품 등록
        Item item = itemFormDto.createItem();

        itemRepository.save(item);

       return item.getId();
    }
    @Value("${uploadPath}")
    private String uploadPath;
    public Long saveItem(ItemFormDto itemFormDto,MultipartFile itemImg) throws Exception{
        // 상품 등록
        Item item = itemFormDto.createItem();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "_" + itemImg.getOriginalFilename();
        File itemImgFile=  new File(uploadPath,fileName);
        itemImg.transferTo(itemImgFile);
        item.setItemImg(fileName);
        item.setItemImgPath(uploadPath+"/"+fileName);
        itemRepository.save(item);

        return item.getId();

    }

    // 전체 상품 조회
    public List<ItemFormDto> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.of(s)));  // 각 상품 정보를 ItemFormDto로 변환하여 리스트에 추가
        return itemFormDtos;
    }
}
