package com.example.demo6.repository;

import com.example.demo6.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
  List<Item> findByItemName(String itemName);

  List<Item> findByItemDetail(String itemDetail);

  List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

  List<Item> findByIdxLessThan(long idx);

  List<Item> findByIdxLessThanOrderByIdxDesc(long idx);

  List<Item> findByItemNameContaining(String itemName);

  void deleteByIdxLessThan(long idx);

  @Query("select i from Item i where itemName like %:itemName%")
  List<Item> searchItemNameLike(String itemName);

  @Query("select i from Item i where price >= :price and itemDetail like %:itemDetail% order by idx desc")
  List<Item> searchItemPriceItemDetail(int price, String itemDetail);

  @Modifying(clearAutomatically = true) // Dirty Checking
  @Query("update Item set price = price+:price where idx >= :idx")
  void updateItemPrice(int idx, int price);

  @Query(value="select * from item_product where item_name like %:itemName% order by price desc", nativeQuery = true)
  List<Item> searchItemNameLikeN(String itemName);
}
