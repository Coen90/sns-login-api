package com.shop.projectlion.api.item.controller;

import com.shop.projectlion.api.item.dto.GetItemResponseDto;
import com.shop.projectlion.api.item.dto.UpdateItemDto;
import com.shop.projectlion.api.item.service.ApiItemService;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.error.exception.InvalidValueException;
import com.shop.projectlion.global.resolver.MemberEmail;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/items")
public class ApiItemController {

    private final ApiItemService apiItemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<GetItemResponseDto> getItem(@PathVariable Long itemId, @MemberEmail String email) {
        GetItemResponseDto getItemResponseDto = apiItemService.getItem(itemId);
        return ResponseEntity.ok(getItemResponseDto);
    }

    @ApiOperation(value = "상품 수정 API")
    @PatchMapping("/{itemId}")
    public ResponseEntity<UpdateItemDto.Response> updateItem(@PathVariable Long itemId, @Validated @RequestBody UpdateItemDto.Request updateItemRequestDto) {
        if(updateItemRequestDto.getItemId() != itemId) {
            throw new InvalidValueException(ErrorCode.ITEM_ID_NOT_SAME);
        }

        UpdateItemDto.Response updateItemResponseDto = apiItemService.updateItem(updateItemRequestDto);
        return ResponseEntity.ok(updateItemResponseDto);
    }
}
