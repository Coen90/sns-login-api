package com.shop.projectlion.domain.delivery.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(length = 50, nullable = false)
    private String deliveryName;

    @Column(nullable = false)
    private Integer deliveryFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Delivery(Long deliveryId, String deliveryName, Integer deliveryFee, Member member) {
        this.deliveryId = deliveryId;
        this.deliveryName = deliveryName;
        this.deliveryFee = deliveryFee;
        this.member = member;
    }


}