package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.CartDTO;
import com.yayum.tour_info_service_server.dto.FolderAllDTO;
import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    //폴더 내용 모두 들고오기
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FolderAllDTO>> allFolder(@RequestParam(value = "mno")Long mno){
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
    @DeleteMapping(value = "/delete/{fno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> remove(@PathVariable Long fno){
        folderService.remove(fno);
        return new ResponseEntity<>(fno,HttpStatus.OK);
    }

    //스팟 등록
    @PostMapping(value = "/spot-append",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long>spotAdd(@RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(folderService.addSpot(cartDTO),HttpStatus.OK);
    }

    //스팟 삭제
    @DeleteMapping(value="/spot-delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>spotDelete(@RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(folderService.deleteSpot(cartDTO),HttpStatus.OK);
    }

}
