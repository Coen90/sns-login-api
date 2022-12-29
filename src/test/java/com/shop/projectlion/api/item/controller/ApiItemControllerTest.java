package com.shop.projectlion.api.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.projectlion.api.item.dto.UpdateItemDto;
import com.shop.projectlion.api.item.service.ApiItemService;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ApiItemControllerTest {

    @InjectMocks
    private ApiItemController apiItemController;

    @Mock
    private ApiItemService apiItemService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiItemController)
                .build();
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItem() throws Exception {
        // give (상황)
        Long itemId = 1L;
        UpdateItemDto.Request updateItemRequestDto = UpdateItemDto.Request.builder()
                .itemId(itemId)
                .itemName("테스트 상품")
                .itemDetail("테스트 상품 상세")
                .price(3000)
                .stockNumber(100)
                .itemSellStatus(ItemSellStatus.SELL)
                .deliveryId(99999l)
                .build();

        UpdateItemDto.Response updateItemResponseDto = UpdateItemDto.Response.builder()
                .itemId(itemId)
                .itemName("테스트 상품")
                .itemDetail("테스트 상품 상세")
                .price(3000)
                .stockNumber(100)
                .itemSellStatus(ItemSellStatus.SELL)
                .deliveryId(99999l)
                .build();

        Mockito.when(apiItemService.updateItem(updateItemRequestDto)).thenReturn(updateItemResponseDto);

        // when (동작)
        ResultActions result = mockMvc.perform(patch("/api/admin/items/{itemId}", itemId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateItemRequestDto)));


        // then (결과)
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName", is(updateItemResponseDto.getItemName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemDetail", is(updateItemResponseDto.getItemDetail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(updateItemResponseDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockNumber", is(updateItemResponseDto.getStockNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemSellStatus", is(updateItemResponseDto.getItemSellStatus().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryId", is(99999)));
        // 3535
    }


}