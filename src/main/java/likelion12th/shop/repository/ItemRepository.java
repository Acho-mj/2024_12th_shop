package likelion12th.shop.repository;

import likelion12th.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 쿼리메소드
    List<Item> findByItemName(String itemName);

    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // @Query with JPQL
    @Query("select i from Item i where i.itemDetail like %:itemDetail%  order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // navtiveQuery -> nativeQuery = true
    @Query(value = "select  * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

    // 상품명으로 상품 조회
    List<Item> findByItemNameContainingIgnoreCase(String itemName);
}
