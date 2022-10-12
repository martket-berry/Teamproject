//package com.example.project.Service.Data;
//
//import com.example.project.Repository.FileUploadRepository.FileUploadRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@Transactional
//public class FileUploadService {
//
//    @Autowired
//    FileUploadRepository fileUploadRepository;
//
//    @Transactional
//    public void save(Files files){
//        Files f = new Files();
//        f.setFilename(files.getFilename());
//        f.setFileOriName(files.getFileOriName());
//        f.setFileUrl(files.getFileUrl());
//
//        fileUploadRepository.save(f);
//    }
//}
