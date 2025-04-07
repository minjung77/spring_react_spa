package com.example.zzyzzy.semiprojectv2.controller;

import com.example.zzyzzy.semiprojectv2.domain.Gallery;
import com.example.zzyzzy.semiprojectv2.domain.Pds;
import com.example.zzyzzy.semiprojectv2.domain.PdsReplyDTO;
import com.example.zzyzzy.semiprojectv2.service.GalleryService;
import com.example.zzyzzy.semiprojectv2.service.PdsService;
import com.example.zzyzzy.semiprojectv2.utils.GoogleRecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins={"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@Controller
@RequestMapping("/api/pds")
@RequiredArgsConstructor
public class PdsController {
    private final GoogleRecaptchaService googleRecaptchaService;
    private final PdsService pdsService;
    @Value("${savePdsDir}") private String savePdsDir;
//    Query String (질의문자열)
//    URL의 ? 뒤에 key=value 형태로 데이터를 전달하는 방식
//    ex) /users?name=John&age=30에서 name과 age가 Query String 매개변수
//    스프링 부트에서는 @RequestParam
//    어노테이션을 사용하여 처리
//    검색 조건, 필터링, 정렬 등 복잡한 데이터를 전달하기에 적합
//
//    Path Variable (경로 변수)
//    URL 경로 자체에 데이터를 포함시키는 방식
//    ex) /users/John/30에서 John, 30이 Path Variable로 사용
//    스프링 부트에서는 @PathVariable
//    어노테이션을 사용하여 처리
//    RESTful API 설계에서 자원의 식별자로 사용하기에 적합


    @GetMapping("view/{pno}")
    public ResponseEntity<?> view(@PathVariable int pno){
        PdsReplyDTO resreply = pdsService.readOnePdsReply(pno);

        return new ResponseEntity<>(resreply, HttpStatus.OK);
    }


    @PostMapping("/write")
    public ResponseEntity<?> writeok(Pds pds, List<MultipartFile> panames,
                                     @RequestParam("g-recaptcha-response") String gRrecaptchaResponse) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        log.info("submit 된 자료실 정보1 ::: {}", pds);
        log.info("submit 된 자료실 정보2 ::: {}", panames);

        try{
            if(!googleRecaptchaService.verifyRecaptcha(gRrecaptchaResponse)){
                throw new IllegalStateException("자동가입방지 코드 오류!!!");
            }

            if(pdsService.newPds(pds, panames)){
                log.info("aaa");
                response = ResponseEntity.ok().body("파일 업로드 성공!");
            }
        }catch (IllegalStateException e){
            response = ResponseEntity.badRequest().body(e.getMessage());
        }finally{
        }
        return response;
    }

    @GetMapping("/down/{fname}")
    public ResponseEntity<?> down(@PathVariable String fname){
        //다운로드할 실제 파일 경로를 알아냄
        File file = new File(savePdsDir + fname);
        if(!file.exists()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일이 존재하지 않습니다.");
        }
        HttpHeaders headers = new HttpHeaders();
        //다운로드 시 저장할 파일명 지정
        headers.setContentDisposition(ContentDisposition.attachment().filename(fname).build());
        //다운로드 시 다운로드 할 파일의 유형 지정
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }
}
