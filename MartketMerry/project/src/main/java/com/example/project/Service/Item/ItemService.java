package com.example.project.Service.Item;

import com.example.project.Entity.Item.Item;
//import com.example.project.Entity.Item.fileUpload;

import java.util.List;

//엔티티의
public interface ItemService {

    Long insertItems(Item item);
    //Long 이 Item으로 바뀜

    List<Item> itemLists();

    List<Item> itemListss(List<Item> itemList);


     void updateItem(Item item);     // 상품 수정

    void deleteItem(Item item);     // 상품 삭제

//    void insertfileUpload(fileUpload entity);
}
