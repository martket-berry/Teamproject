package com.example.project.Service.Item;

import com.example.project.Entity.Item.FileUploadEntity;
import com.example.project.Entity.Item.Item;
//import com.example.project.Entity.Item.fileUpload;
import com.example.project.Repository.FileUploadRepository.FileUploadRepository;
import com.example.project.Repository.Item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    private final ItemRepository itemRepo;
    //서비스에서 레파지토리의 JPA를 이용하기 위하여 작성
    private final FileUploadRepository fileUploadRepo;
    @Autowired  //오토와이어드 왜 쓰는지?
    protected ItemServiceImp(ItemRepository itemRepo, FileUploadRepository fileUploadRepo){
        this.itemRepo = itemRepo;
        //왜 쓰는지?
        this.fileUploadRepo = fileUploadRepo;
    }

    @Override
    public Long insertItems(Item item) {
//        itemRepo.save(item);
        return itemRepo.save(item).getItemId();
    }

    //아이템 리스트
    @Override
    public List<Item> itemLists() {
        return itemRepo.findAll();
    }

    @Override
    public List<Item> itemListss(List<Item> itemList) {
        return itemList;
    }

    @Override
    public List<Item> getItemList() {
        return itemRepo.findAll();
    }

    @Override
    public List<Item> getItemListss(List<Item> getItemList) {
        return getItemList();
    }

    @Override   //상품 수정
    public void updateItem(Item item) {
        Item updateItem = itemRepo.findById(item.getItemId()).get();

//        updateItem.setPhoto(item.getPhoto());                   //사진
        updateItem.setMainCategory(item.getMainCategory());     //대분류
        updateItem.setSubCategory(item.getSubCategory());       //소분류
        updateItem.setItemName(item.getItemName());             //이름
        updateItem.setItemText(item.getItemText());             //설명
        updateItem.setPrice(item.getPrice());                   //가격
        updateItem.setDelivery(item.getDelivery());             //배송 선택
        updateItem.setSeller(item.getSeller());                 //업체,판매자명
        updateItem.setPacking(item.getPacking());               //포장 타입
        updateItem.setOrigin(item.getOrigin());                 //원산지
        updateItem.setShelfLife(item.getShelfLife());           //유통기한
        updateItem.setStock(item.getStock());                   //재고 수량
//        updateItem.setDetailText(item.getDetailText());         //상세 설명
//        updateItem.setDetailPhoto(item.getDetailPhoto());       //상세 사진

    }

    @Override   //상품 삭제
    public void deleteItem(Item item) {
        itemRepo.deleteById(item.getItemId());
    }

    @Override
    public Item getItem(Item item) {
        return null;
    }

    @Override
    public Long insertFileUploadEntity(FileUploadEntity fileUploadEntity) {
        return fileUploadRepo.save(fileUploadEntity).getItemSeq();
    }

    @Override
    public List<FileUploadEntity> getFileUploadEntity(Long item_seq) {
        return fileUploadRepo.findByItemSeq(item_seq);
    }

}
