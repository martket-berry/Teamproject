package com.example.project.Repository.FileUploadRepository;

import com.example.project.Entity.Item.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {
    //findBy: 튜플을 찾겠다

    List<FileUploadEntity> findByItemSeq(Long itemSeq);
    //item Entitiy의 기본키는 id 인데 가져오는곳에서 seq로 써서 일단 seq로 써봄
}
