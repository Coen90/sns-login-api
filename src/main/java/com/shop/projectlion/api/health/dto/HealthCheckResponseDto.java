package com.shop.projectlion.api.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class HealthCheckResponseDto {

    private boolean status;
    private String health;

}
