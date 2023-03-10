package com.shop.projectlion.domain.itemimage.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import com.shop.projectlion.domain.itemimage.repository.ItemImageRepository;

import com.shop.projectlion.infra.file.FileService;
import com.shop.projectlion.infra.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    @Transactional
    public void saveItemImages(Item item, List<MultipartFile> itemImageFiles) throws IOException {
        for(int i=0;i<itemImageFiles.size();i++) {
            Boolean isRepImage = i == 0;
            saveItemImage(item, itemImageFiles.get(i), isRepImage);
        }
    }

    @Transactional
    public void saveItemImage(Item item, MultipartFile itemImageFile, Boolean isRepImage) throws IOException {

        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
        String originalFilename = uploadFile != null ? uploadFile.getOriginalFileName() : "";
        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";

        ItemImage itemImage = ItemImage.builder()
                .imageName(storeFileName)
                .imageUrl(imageUrl)
                .originalImageName(originalFilename)
                .isRepImage(isRepImage)
                .build();

        ItemImage saveItemImage = ItemImage.createItemImage(itemImage, item);
        itemImageRepository.save(saveItemImage);
    }

    public List<ItemImage> findByItemOrderByItemImageIdAsc(Item item) {
        return itemImageRepository.findByItemOrderByItemImageIdAsc(item);
    }

    @Transactional
    public void updateItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws IOException {
        // ?????? ?????? ????????? ????????? ???????????? ?????? ?????? ??????
        if(StringUtils.hasText(itemImage.getImageName())) {
            fileService.deleteFile(itemImage.getImageUrl());
        }

        // ????????? ????????? ?????? ??????
        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String originalFilename = uploadFile.getOriginalFileName();
        String storeFileName = uploadFile.getStoreFileName();
        String imageUrl = IMAGE_URL_PREFIX + storeFileName;

        // ?????? ????????? ?????? ?????? ????????????
        itemImage.updateItemImage(originalFilename, storeFileName, imageUrl);
    }

    @Transactional
    public void deleteItemImage(ItemImage itemImage) throws IOException {
        // ?????? ????????? ?????? ??????
        String fileUploadUrl = fileService.getFullFileUploadPath(itemImage.getImageName());
        fileService.deleteFile(fileUploadUrl);
        // ????????? ?????? ?????????
        itemImage.initImageInfo();
    }

    public ItemImage findByItemAndIsRepImage(Item item, boolean isRepImage) {
        return itemImageRepository.findByItemAndIsRepImage(item, isRepImage);
    }

}