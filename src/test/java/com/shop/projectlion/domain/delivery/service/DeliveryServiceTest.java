package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @InjectMocks
    DeliveryService deliveryService;

    @Mock
    DeliveryRepository deliveryRepository;

    @Test
    void findByDeliveryId() {

        // given
        Long deliveryId = 1L;
        Delivery delivery = Delivery.builder()
                .deliveryId(deliveryId)
                .deliveryFee(3000)
                .deliveryName("마포구 물류센터")
                .build();

        Optional<Delivery> optionalDelivery = Optional.of(delivery);
        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(optionalDelivery);

        // when
        Delivery savedDelivery = deliveryService.findByDeliveryId(deliveryId);

        // then
        Assertions.assertThat(savedDelivery.getDeliveryFee()).isEqualTo(delivery.getDeliveryFee());
        Assertions.assertThat(savedDelivery.getDeliveryName()).isEqualTo(delivery.getDeliveryName());
    }


}