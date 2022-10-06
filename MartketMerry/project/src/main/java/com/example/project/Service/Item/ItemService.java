package com.example.project.Service.Item;

import com.example.project.Entity.Item.Item;

//엔티티의
public interface ItemService {

    void insertItem(Item item);     // 상품 등록
    // void를 쓰는 이유 :
//    void updateItem(Item item);     // 상품 수정 (할건가?)

    void deleteItem(Item item);     // 상품 삭제

    Item getItem(Item item);        //상품 불러오기? (멤버에 있길래 써봄)
}
