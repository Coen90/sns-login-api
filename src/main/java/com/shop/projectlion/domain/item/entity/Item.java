package com.shop.projectlion.domain.item.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemSellStatus itemSellStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Builder
    public Item(String itemName, int price, Integer stockNumber, String itemDetail,
                ItemSellStatus itemSellStatus, Member member, Delivery delivery) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.member = member;
        this.delivery = delivery;
    }

    public static Item createItem(Item item, Member member, Delivery delivery) {
        return Item.builder()
                .itemName(item.getItemName())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .member(member)
                .delivery(delivery)
                .build();
    }

    public void updateItem(Item updateItem) {
        this.itemName = updateItem.getItemName();
        this.price = updateItem.getPrice();
        this.stockNumber = updateItem.getStockNumber();
        this.itemDetail = updateItem.getItemDetail();
        this.itemSellStatus = updateItem.getItemSellStatus();
    }

    public void updateDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void decreaseStock(Integer stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new BusinessException(ErrorCode.REST_STOCK_NOT_EXISTS.getMessage() + " (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
        if(this.stockNumber == 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
    }

    public void increaseStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}