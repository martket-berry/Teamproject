package com.example.project.Controller.Item;

import com.example.project.Entity.Item.FileUploadEntity;
import com.example.project.Entity.Item.Item;
//import com.example.project.Entity.Item.fileUpload;
import com.example.project.Service.Item.ItemService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static oracle.security.crypto.util.Utils.toByteArray;

@Builder
@Slf4j
@Controller //디스패처 서블릿이 컨트롤러를 찾기 위해서 @Controller라는 어노테이션을 선언
@RequestMapping(path = "/otherPage")   //미리 경로를 지정해줘서 앞 경로를 생략 가능하게 됨
public class ItemController {
    //private final을 쓰는 이유 = final은 불변성이라 컨트롤러가 안심하고 사용 가능
    private final ItemService itemService;

    //Autowired는 왜 쓰이는가? = controller가 service를 주입당하겠다고 선언
    @Autowired
    private ItemController(ItemService itemService) {
        this.itemService = itemService; // 생성자 주입 방식
    }


    @GetMapping("/addItem")
    public String insertItems(Item item, Model model) {
        System.out.println("get addItem1");
        return "/otherPage/addItem";
    }

    @PostMapping("/addItem")
    public String insertItemss(Model model ,Item item,
                               @Nullable@RequestParam("uploadPhoto")MultipartFile[] uploadPhoto) {
        System.out.println("post addItem1");
        System.out.println(item.getItemId());
        try {
            long file_seq = itemService.insertItems(item);
            List<FileUploadEntity> list = new ArrayList<>();
            for (MultipartFile file : uploadPhoto) {
                if (!file.isEmpty()) {
                    FileUploadEntity entity = new FileUploadEntity(
                            null,
                            UUID.randomUUID().toString(),
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            file_seq,
                            null
                    );
                    itemService.insertFileUploadEntity(entity);
                    list.add(entity);
                    File newFileName = new File(entity.getUuid()+ "_" +entity.getOriginalFileName());
                    System.out.println(file.getOriginalFilename());
                    file.transferTo(newFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        itemService.insertItems(item);
        System.out.println(item);
        return "redirect:/otherPage/addItem";
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("uploadPhoto")MultipartFile[] uploadPhoto) throws IOException {
        log.info("img load session");
        List<FileUploadEntity> list = new ArrayList<>();
        for(MultipartFile file : uploadPhoto) {
            if(!file.isEmpty()) {
                FileUploadEntity entity = new FileUploadEntity(
                        null,
                        UUID.randomUUID().toString(),
                        file.getContentType(),
                        file.getName(),
                        file.getOriginalFilename(),
                        null,
                        null
                );
                Long output = itemService.insertFileUploadEntity(entity);
                log.info("seq check");
                log.info(output.toString());
                list.add(entity);
                File newFileName = new File(entity.getUuid() + "_" + entity.getOriginalFileName());
                file.transferTo(newFileName);
            }
        }
        return "/otherPage/addItem";
    }

    @GetMapping("/getItem")
    public String getItem(Item item, Model model) {
        List<Item> itemList = new ArrayList<>();
        FileUploadEntity fileUploadEntity = (FileUploadEntity) itemService.getFileUploadEntity(item.getItemId());
        String path = "/Item/image/"+fileUploadEntity.getUuid()+"_"+fileUploadEntity.getOriginalFileName();
        model.addAttribute("item", itemList);
//        model.addAttribute("itemList", itemService.getItemListss(
//                                                itemService.getItemList()));
        //        model.addAttribute("itemList", itemService.getItem(item));
//        model.addAttribute("itemPrv", itemService.getPagesSortIndex(item));
//        model.addAttribute("item", itemService.getItemListss(
//                                                itemService.getItemList()));
        model.addAttribute("imgLoading",path);
        return "/otherPage/addItem";
    }

    @GetMapping("viewImage/{imgname}")
    public ResponseEntity<byte[]> viewImage(@PathVariable("imgname")String input_imgName) throws IOException {
        //ResponseEntity<byte[]>: http프로토콜을 통해서 byte데이터를 전달하는 객체, byte(소문자 = 기본타입) []배열
        //@PathVariable: URL주소의 값을 받아옴
        String path = "C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\Spring Boot\\\\\\\\Project_board\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload\\\\\\\\"+input_imgName;
        //데이터(이미지)를 전송하기 위한 객체로써 java에서는 항상 데이터를 스트림타입으로 전달
        InputStream inputStream = new FileInputStream(path);
        //byte배열로 변환
//        byte[] imgByteArr = toByteArray(inputStream);
        inputStream.close();
        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송
        return new ResponseEntity<byte[]>(
//                imgByteArr,
                HttpStatus.OK);
    }


    @GetMapping(value = "/image/{imagename}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imageLoading(@PathVariable("imagename")String imagename) throws IOException{
        //ReponseEntity<byte[]>: 메소드 리턴타임으로 이미지 데이터를 송신하기 위한 객체<바이트 배열>
        //throws IOException: 스트립방식으로 데이터를 저송할 때 도중에 오류가 날 경우를 찾기 위해서 선언한 Exception

        String path="C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\test\\\\\\\\cloneproject\\\\\\\\MartketMerry\\\\\\\\project\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload"+imagename;
        //File을 컴퓨터가 이해하기 위해서 Stream 배열을 만들어서 작업
        //객체(데이터 저장) : String, int, double
        //Stream객체는 파일을 컴퓨터가 cpu에서 바로 읽어들일 수 있도록 하는 객체
        FileInputStream fis = new FileInputStream(path);
        //Buffered: CPU에서 데이터를 읽어올 때 메모리와 캐시 사이에서 CPU와의 속도 차이를 줄이기 위한 중간 저장 위치
        BufferedInputStream bis = new BufferedInputStream(fis);
        //byte배열로 전환하여 ResponseEntity를 통해 클라이언트에게 데이터 전달
        //HTTP프로토콜은 바이트 단위(배열)로 데이터를 주고 받음
        byte[] imgByteArr = bis.readAllBytes();
        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송

        //http 프로토콜은 btye배열로 데이터를 주고 받기 때문에 stream이나 버퍼를 통해 전환
        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
    }


//    @PostMapping("/addItem")
//    public String insertItemss(Item item,
//                               @Nullable@RequestParam("photo")MultipartFile[] photo) {
//        System.out.println("post addItem1");
//        System.out.println(item.getItemId());
//        for(MultipartFile file : photo) {
//            if (!file.isEmpty()) {
//                System.out.println(file.getOriginalFilename());
//            }
//        }
//        itemService.insertItems(item);
//
//        return "redirect:/addItem";
//    }

//    @PostMapping("/addItem")
//    public String insertItemss(Item item,
//                               @Nullable@RequestParam("photo")MultipartFile[] photo) throws IOException {
//        System.out.println("post addItem1");
//        System.out.println(item.getItemId());
//        for(MultipartFile file : photo) {
//            if (!file.isEmpty()) {
//            FileUploadEntity entity = new FileUploadEntity(null,
//                     UUID.randomUUID().toString(),
//                     file.getContentType(),
//                     file.getName(),
//                      file.getOriginalFilename(),
//                      null
//                        );
//            }
//        }
//        itemService.insertItems(item);
//        return "redirect:/addItem";
//    }


//    @PostMapping("/addItem")
//    public void insertItemss(MultipartFile[] photo) {
//        String path="C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\test\\\\\\\\cloneproject\\\\\\\\MartketMerry\\\\\\\\project\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload";
//
//        for(MultipartFile multipartFile : photo) {
//           File saveFile = new File(path, multipartFile.getOriginalFilename());
//
//        }
//        itemService.insertItems(item);
//        return "redirect:/addItem";
//    }

//    @PostMapping("/addItem")
//    public String uploadFile(@RequestParam MultipartFile files) throws IOException {
//        List<FileUploadEntity> list = new ArrayList<>();
//
//        String sourceFileName = files.getOriginalFilename();
//
//        String sourceFileNameExtension = FilenameUils.getExtension(sourceFileName).toLowerCase();
//
//        FilenameUtils.removeExtension(sourceFileName);
//
//        File destinationFile;
//        String destinationFileName;
//        String fileUrl = "C:\\\\Users\\\\82102\\\\Desktop\\\\coding\\\\Spring Boot\\\\Project_board\\\\src\\\\main\\\\resources\\\\static\\\\upload\\\\";
//
//        do{
//            destinationFileName = RandomStringUtils.randomAlphanumeric + sourceFileNameExtension;
//            destinationFile = new File(fileUrl + destinationFileName);
//        }while (destinationFile.exists());
//
//        destinationFile.getParentFile().mkdirs();
//        files.transferTo(destinationFile);
//
//        file.setFilename(destinationFileName);
//        file.setFileOriginalName(sourceFileName);
//        file.setFileUrl(fileUrl);
//        filesSercive.save(file);
//
//        return "redirect:/addItem";
//    }

//    @GetMapping("/getitemList")
//    public String itemList(Model model){
////        List<Member> memberList = memberService.getMemberList();
//        model.addAttribute("item",
//                itemService.getItemListss(
//                        itemService.getItemList()));
//        return "/otherPage/addItem";
//    }


//    @GetMapping("/addItem")
//    public String insertItems(Item item, Model model) {
//        System.out.println("get addItem");
//        Item item1 = new Item(
//                item.getItemId(),
//                item.getPhoto(),
//                item.getMainCategory(),
//                item.getSubCategory(),
//                item.getItemName(),
//                item.getItemText(),
//                item.getPrice(),
//                item.getDelivery(),
//                item.getSeller(),
//                item.getPacking(),
//                item.getOrigin(),
//                item.getShelfLife(),
//                item.getStock(),
//                item.getDetailText(),
//                item.getDetailPhoto()
//        );
//        model.addAttribute("item", item1);
//        return "/otherpage/addItem";
//    }

}
