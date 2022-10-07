package com.example.project.Repository.Item;

import com.example.project.Entity.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

// JpaRepository를 상속 받음
// Entity를 매개체로 CRUD를 가리킴
// <Item, String> = <매개변수 Item (엔티티)와 String(엔티티 내의 기본키의 타입 선언)
// 이후 service 단에서 레파지토리 사용 (JPA의 CRUD를 이용하기 위하여)
public interface ItemRepository extends JpaRepository<Item, Long> {
//    List<Object> findByItemName(String itemName);
    //레파지토리란?
    //Entity를 통해 데이터를 DB에 저장,
    //찾을떄도 JPA의 findBy로 찾는듯


}
