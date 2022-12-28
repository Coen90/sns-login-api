package com.shop.projectlion.domain.itemimage.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_image")
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImageId;

    @Column(nullable = false, length = 500)
    private String imageName; // 실제로 로컬에 저장할 이미지 파일명

    @Column(nullable = false, length = 200)
    private String originalImageName; // 원본 이미지 파일명

    @Column(nullable = false, length = 500)
    private String imageUrl; //이미지 조회 경로

    @Column(nullable = false)
    private Boolean isRepImage; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder
    public ItemImage(String imageName, String originalImageName, String imageUrl, Boolean isRepImage, Item item) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
        this.isRepImage = isRepImage;
        this.item = item;
    }

    public static ItemImage createItemImage(ItemImage itemImage, Item item) {
        return ItemImage.builder()
                .imageName(itemImage.getImageName())
                .imageUrl(itemImage.getImageUrl())
                .originalImageName(itemImage.getOriginalImageName())
                .isRepImage(itemImage.getIsRepImage())
                .item(item)
                .build();
    }

    public void updateItemImage(String originalImageName, String imageName, String imageUrl){
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public void initImageInfo() {
        this.originalImageName = "";
        this.imageName = "";
        this.imageUrl = "";
    }

}