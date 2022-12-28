package com.shop.projectlion.api.item.controller;

import com.shop.projectlion.api.item.dto.GetItemResponseDto;
import com.shop.projectlion.api.item.service.ApiItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/items")
public class ApiItemController {

    private final ApiItemService apiItemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<GetItemResponseDto> getItem(@PathVariable Long itemId) {
        GetItemResponseDto getItemResponseDto = apiItemService.getItem(itemId);
        return ResponseEntity.ok(getItemResponseDto);
    }
}
