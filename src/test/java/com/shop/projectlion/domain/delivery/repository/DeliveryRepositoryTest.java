package com.shop.projectlion.domain.delivery.repository;


import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class DeliveryRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    @DisplayName("배송 조회 테스트")
    void findByMember() {

        // given
        Member member = Member.builder()
                .email("test@test.com")
                .memberType(MemberType.GENERAL)
                .role(Role.ADMIN)
                .memberName("홍길동")
                .build();
        Member savedMember = memberRepository.save(member);

        Delivery delivery = Delivery.builder()
                .deliveryName("마포구 물류센터")
                .deliveryFee(3000)
                .member(member)
                .build();
        deliveryRepository.save(delivery);

        // when
        Optional<List<Delivery>> deliveries = deliveryRepository.findByMember(savedMember);
        Delivery savedDelivery = deliveries.get().get(0);

        // then
        Assertions.assertThat(savedDelivery.getDeliveryId()).isNotNull();
        Assertions.assertThat(savedDelivery.getDeliveryName()).isEqualTo(delivery.getDeliveryName());
        Assertions.assertThat(savedDelivery.getDeliveryFee()).isEqualTo(delivery.getDeliveryFee());
    }

}