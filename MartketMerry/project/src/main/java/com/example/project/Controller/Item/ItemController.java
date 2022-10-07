package com.example.project.Controller.Item;

import com.example.project.Entity.Item.Item;
//import com.example.project.Entity.Item.fileUpload;
import com.example.project.Service.Item.ItemService;
import lombok.Builder;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
@Controller //디스패처 서블릿이 컨트롤러를 찾기 위해서 @Controller라는 어노테이션을 선언
@RequestMapping(path = "/otherpage")   //미리 경로를 지정해줘서 앞 경로를 생략 가능하게 됨
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
        System.out.println("get addItem");
        Item item1 = new Item(
                item.getItemId(),
                item.getPhoto(),
                item.getMainCategory(),
                item.getSubCategory(),
                item.getItemName(),
                item.getItemText(),
                item.getPrice(),
                item.getDelivery(),
                item.getSeller(),
                item.getPacking(),
                item.getOrigin(),
                item.getShelfLife(),
                item.getStock(),
                item.getDetailText(),
                item.getDetailPhoto()
        );
        model.addAttribute("item", item1);
        return "/otherpage/addItem";
    }

    @PostMapping("/addItem")
    public String insertItemss(Item item, Model model) {
        itemService.insertItems(item);
        return "redirect:/otherpage/addItem";
    }

//    @PostMapping("/insertBoard")
//    public String insertItems(Item item, @Nullable @RequestParam("uploadfile") MultipartFile[] uploadfile){
//        //@Nullable@RequestParam("uploadfile")MultipartFile[]:
//        //MultipartFile를 클라이언트에서 받아오고, 데이터가 없더라도 허용(@Nullable)
//        try {
//            //boardService.insertBoard 메소드에서는 DB에 데이터를 저장하고 저장된 board_seq를 리턴 받음
//            Long item_id = itemService.insertItem(item);
//            List<fileUpload> list = new ArrayList<>();
//            for(MultipartFile file : uploadfile){
//                //MultipartFile로 클라이언트에서 온 데이터가 무결성 조건에 성립을 안하거나 메타데이터가 없거나 문제가 생길 여지를 if문으로 처리
//                if(!file.isEmpty()){
//                    fileUpload entity = new fileUpload(
//                            null,
//                            UUID.randomUUID().toString(),
//                            file.getContentType(),
//                            file.getName(),
//                            file.getOriginalFilename(),
//                            item_id
//                    );
//                    //fileuploadtable에 데이터 저장
//                    itemService.insertfileUpload(entity);
//                    list.add(entity);
//                    File newFileName = new File(entity.getUuid() + "_" + entity.getOriginalFilename());
//                    //서버에 이미지 파일 업로드(저장)
//                    file.transferTo(newFileName);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        } return "redirect:/contents/items/vegetable";
//    }

//    @GetMapping("/getBoard")    //09.01
//    public String getBoard(Board board, Model model) {
//        FileUploadEntity fileUploadEntity = boardService.getFileUploadEntity2(board.getSeq());
//        String path = "/board/image/"+fileUploadEntity.getUuid()+"_"+fileUploadEntity.getOriginalFilename();
//
//        model.addAttribute("board", boardService.getBoard(board));
//        model.addAttribute("boardPrv", boardService.getPagesSortIndex(board));
//        model.addAttribute("imgLoading", path);
//        return "/board/getBoard";
//    }
//
//    @GetMapping("viewImage/{imgname}")
//    public ResponseEntity<byte[]> viewImage(@PathVariable("imgname")String input_imgName) throws IOException {
//        //ResponseEntity<byte[]>: http프로토콜을 통해서 byte데이터를 전달하는 객체, byte(소문자 = 기본타입) []배열
//        //@PathVariable: URL주소의 값을 받아옴
//        String path = "C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\Spring Boot\\\\\\\\Project_board\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload\\\\\\\\"+input_imgName;
//        //데이터(이미지)를 전송하기 위한 객체로써 java에서는 항상 데이터를 스트림타입으로 전달
//        InputStream inputStream = new FileInputStream(path);
//        //byte배열로 변환
//        byte[] imgByteArr = toByteArray(inputStream);
//        inputStream.close();
//        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송
//        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/image/{imagename}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> imageLoading(@PathVariable("imagename")String imagename) throws IOException{
//        //ReponseEntity<byte[]>: 메소드 리턴타임으로 이미지 데이터를 송신하기 위한 객체<바이트 배열>
//        //throws IOException: 스트립방식으로 데이터를 저송할 때 도중에 오류가 날 경우를 찾기 위해서 선언한 Exception
//
//        String path = "C:\\\\\\\\Users\\\\\\\\82102\\\\\\\\Desktop\\\\\\\\coding\\\\\\\\Spring Boot\\\\\\\\Project_board\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\static\\\\\\\\upload\\\\\\\\"+imagename;
//        //File을 컴퓨터가 이해하기 위해서 Stream 배열을 만들어서 작업
//        //객체(데이터 저장) : String, int, double
//        //Stream객체는 파일을 컴퓨터가 cpu에서 바로 읽어들일 수 있도록 하는 객체
//        FileInputStream fis = new FileInputStream(path);
//        //Buffered: CPU에서 데이터를 읽어올 때 메모리와 캐시 사이에서 CPU와의 속도 차이를 줄이기 위한 중간 저장 위치
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        //byte배열로 전환하여 ResponseEntity를 통해 클라이언트에게 데이터 전달
//        //HTTP프로토콜은 바이트 단위(배열)로 데이터를 주고 받음
//        byte[] imgByteArr = bis.readAllBytes();
//        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송
//
//        //http 프로토콜은 btye배열로 데이터를 주고 받기 때문에 stream이나 버퍼를 통해 전환
//        return new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);
//    }
}
