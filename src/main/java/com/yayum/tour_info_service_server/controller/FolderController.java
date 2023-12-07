package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    //폴더 내용 모두 들고오기
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object[]>> allFolder(@RequestParam(value = "mno")Long mno){
      return new ResponseEntity<>(folderService.getAllFolder(mno), HttpStatus.OK);
    }

    //폴더명 모두 조회
    @GetMapping(value = "/title",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>>folderNames(@RequestParam(value = "mno")Long mno){
        return new ResponseEntity<>(folderService.getTitle(mno),HttpStatus.OK);
    }

    //폴더 등록
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long>register(@RequestBody FolderDTO folderDTO){
        Long num=folderService.register(folderDTO);
        return new ResponseEntity<>(num,HttpStatus.OK);
    }

    //폴더명 수정
    @PutMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long>modify(@RequestBody FolderDTO folderDTO){
        Long num=folderService.modify(folderDTO);
        return new ResponseEntity<>(num,HttpStatus.OK);
    }

    //폴더 삭제
    @DeleteMapping(value = "/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity remove(@RequestBody Long fno){
        folderService.remove(fno);
        return new ResponseEntity(HttpStatus.OK);
    }

}
