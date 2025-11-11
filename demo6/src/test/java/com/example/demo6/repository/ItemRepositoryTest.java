package com.example.demo6.repository;

import com.example.demo6.constant.ItemSellStatus;
import com.example.demo6.entity.Item;
import com.example.demo6.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo6.entity.QItem.item;

@SpringBootTest
class ItemRepositoryTest {
  @Autowired
  ItemRepository itemRepository;
  //@PersistenceContext
  @Autowired
  private EntityManager em;

  @Test
  @DisplayName("상품등록 연습.")
  public void createItemTest() {
    Item item = new Item();
    //item_product.setItemName("시험상품");
    item = Item.builder()
            .itemName("시험상품")
            .price(30000)
            .stockNumber(40)
            .itemDetail("시험상품의 상세설명")
            .regTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .itemSellStatus(ItemSellStatus.SELL)
            .build();
    System.out.println("상품명: "+item.getItemName());
    System.out.println(item);

    item = itemRepository.save(item);

    System.out.println(item);
  }

  @DisplayName("상품등록.")
  public void createItemListTest() {
    for(int i=1; i<=12; i++) {
      Item item = Item.builder()
              .itemName("상품"+i)
              .price(1000+i)
              .stockNumber(10+i)
              .itemDetail("상품"+i+"의 상세설명")
              .itemSellStatus(ItemSellStatus.SELL)
              .regTime(LocalDateTime.now())
              .updateTime(LocalDateTime.now())
              .build();

      itemRepository.save(item);
    }
  }
  @Test
  @DisplayName("상품명 검색")
  public void findByItemNameTest() {
    //createItemListTest();

    List<Item> itemList = itemRepository.findByItemName("상품1");

    System.out.println("작업시작");
    for(Item item : itemList) {
      System.out.println(item);
    }
    System.out.println("작업끝");
  }
  @Test
  @DisplayName("상품명 검색 람다식")
  public void findByItemName2Test() {
    //List<Item> itemList = itemRepository.findByItemName("상품3");

    System.out.println("작업시작");
    //itemList.forEach((item) -> System.out.println(item));
    //itemList.forEach(System.out::println);
    itemRepository.findByItemName("상품5")
            .forEach(System.out::println);
    System.out.println("작업끝");
  }
  @Test
  @DisplayName("상품 상세설명 검색")
  public void findByItemDetailTest() {
    itemRepository.findByItemDetail("상품1의 상세설명")
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("or검색")
  public void findByItemNameOrItemDetailTest() {
    // 상품명이 상품5번이거나 상품상세설명이 상품7의 상세설명인 자료를 검색처리.
    itemRepository.findByItemNameOrItemDetail("상품5", "상품7의 상세설명")
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("LessThen")
  public void findByIdxLessThanTest() {
    // 상품 아이디가 5보다 작은 상품 검색.
    itemRepository.findByIdxLessThan(5)
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("LessThen idx내림차순정렬")
  public void findByIdxLessThanOrderByIdxDescTest() {
    // 상품 아이디가 5보다 작은 상품 검색.
    itemRepository.findByIdxLessThanOrderByIdxDesc(5)
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("LIKE")
  public void findByItemNameLikeTest() {
    // 상품명에 2를 포함하는 상품 검색.
    itemRepository.findByItemNameContaining("2")
            .forEach(System.out::println);
    // 상품명에 1을 포함하는 상품 검색.
    itemRepository.findByItemNameContaining("1")
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("Delete")
  public void deleteByIdTest() {
    // id로 삭제.
    itemRepository.deleteById(5L);
    // 삭제됐는지 확인.
    itemRepository.findAll()
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("Delete")
  public void deleteByIdxLessThanTest() {
    // id가 5보다 작은 상품 삭제.
    itemRepository.deleteByIdxLessThan(5L);
    // 삭제됐는지 확인.
    itemRepository.findAll()
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("update")
  public void updateByIdxTest() {
    // 상품코드가 5번인 상품의 가격을 25000원으로 변경하시오.
    Item item = itemRepository.findById(5L)
            .orElseThrow(() -> new EntityNotFoundException("5번 상품이 존재하지 않습니다."));
    item.setPrice(30000);

    //itemRepository.save(item);
    itemRepository.flush();

    item = itemRepository.findById(5L)
            .orElseThrow(() -> new EntityNotFoundException("5번 상품이 존재하지 않습니다."));
    System.out.println(item);
  }

  @Test
  @DisplayName("JPQL Like")
  public void searchItemNameLikeTest() {
    // 상품명에 1을 포함하는 상품 검색.
    itemRepository.searchItemNameLike("1")
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("JPQL 복합")
  public void searchItemPriceItemDetail() {
    // 가격이 1005원 이상이며 상세셜명에 1이 포함된 자료를 아이디 내림차순으로 정렬해서 출력.
    itemRepository.searchItemPriceItemDetail(1005, "1")
            .forEach(System.out::println);
  }
  @Test
  @DisplayName("JPQL 수정")
  public void updateItemPrice() {
    // 상품의 idx가 5이상인 상품들의 가격을 +1000.
    itemRepository.updateItemPrice(5, 1000);
    itemRepository.findAll()
            .forEach(System.out::println);
  }

  @Test
  @DisplayName("NativeQuery")
  public void searchItemNameLikeN() {
    // 상품명에 1을 포함하는 상품 출력(가격 내림차순).
    itemRepository.searchItemNameLikeN("1")
            .forEach(System.out::println);
  }

  @Test
  @DisplayName("QueryDSL 연습")
  public void querydsl1Test() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    QItem qItem = QItem.item;

    //List<Tuple> fetch = queryFactory.select().from().fetch();
    List<Item> itemList = queryFactory.select(qItem).from(qItem).fetch();
    itemList.forEach(System.out::println);
  }
  @Test
  @DisplayName("QueryDSL 연습")
  public void querydsl2Test() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    List<Item> itemList = queryFactory.select(item).from(item).fetch();
    itemList.forEach(System.out::println);
  }
  @Test
  @DisplayName("QueryDSL 연습")
  public void querydsl3Test() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    //List<Item> itemList = queryFactory.select(item).from(item).where(item.itemSellStatus.eq(ItemSellStatus.SOLD_OUT)).fetch();
    List<Item> itemList = queryFactory.select(item).from(item).where(item.itemName.like("%2%")).orderBy(item.idx.desc()).fetch();
    itemList.forEach(System.out::println);
  }
  @Test
  @DisplayName("QueryDSL 연습")
  public void querydsl4Test() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    String itemDetail = "상세설명";
    int price = 1005;

    List<Item> itemList = queryFactory
            .select(item)
            .from(item)
            .where(item.itemDetail.like("%"+itemDetail+"%").and(item.price.eq(price)))
            .orderBy(item.itemName.desc())
            .fetch();
    itemList.forEach((item) -> System.out.println(item));
  }
  @Test
  @DisplayName("QueryDSL 페이징처리 연습")
  public void querydsl5Test() {
    // 자료가 없을 수도 있을 때 사용(Optional과 같다).
    BooleanBuilder builder = new BooleanBuilder();

    Pageable pageable = PageRequest.of(0,5);
    Page<Item> page = itemRepository.findAll(builder, pageable);
    List<Item> itemList = page.getContent();
    itemList.forEach(System.out::println);
  }
  @Test
  @DisplayName("QueryDSL 페이징처리 연습")
  public void querydsl6Test() {
    String itemDetail = "상세설명";
    int price = 1005;

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(item.itemDetail.like("%"+itemDetail+"%"))
           .and(item.price.gt(price));

    Pageable pageable = PageRequest.of(0,5);
    Page<Item> page = itemRepository.findAll(builder, pageable);
    List<Item> itemList = page.getContent();
    itemList.forEach(System.out::println);
  }
  @Test
  @DisplayName("QueryDSL Join 연습")
  public void query7Test() {

  }
}