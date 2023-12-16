package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.CartDTO;
import com.yayum.tour_info_service_server.dto.FolderAllDTO;
import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.dto.FolderRegistDTO;
import com.yayum.tour_info_service_server.service.FolderService;
import com.yayum.tour_info_service_server.util.ResponseWrapper;
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
    @GetMapping(value = "/all/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<FolderAllDTO>>> allFolder(@PathVariable Long mno){
        List<FolderAllDTO> data=folderService.getAllFolder(mno);
        ResponseWrapper<List<FolderAllDTO>> response=new ResponseWrapper<>(true,data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //폴더명 모두 조회
    @GetMapping(value = "/title/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<String>>>folderNames(@PathVariable Long mno){
        List<String> data=folderService.getTitle(mno);
        ResponseWrapper<List<String>> response=new ResponseWrapper<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //폴더 등록
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>>register(@RequestBody FolderRegistDTO folderRegistDTO){
        ResponseWrapper response=new ResponseWrapper<>(false,null);
        Long data=folderService.register(folderRegistDTO);
        if(data>0){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //폴더명 수정
    @PutMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>>modify(@RequestBody FolderDTO folderDTO){
        ResponseWrapper response=new ResponseWrapper<>(false,null);
        Long data=folderService.modify(folderDTO);
        if(data!=-1l){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //폴더 삭제
    @DeleteMapping(value = "/delete/{fno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>> remove(@PathVariable Long fno){
        ResponseWrapper response = new ResponseWrapper(false,null);
        Long data=folderService.remove(fno);
        if (data!=-1l){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //스팟 등록
    @PostMapping(value = "/spot-append",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>>spotAdd(@RequestBody CartDTO cartDTO){
        ResponseWrapper response=new ResponseWrapper(false,null);
        Long data=folderService.addSpot(cartDTO);
        if (data!=-1l){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //스팟 삭제
    @DeleteMapping(value="/spot-delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<String>>spotDelete(@RequestBody CartDTO cartDTO){
        ResponseWrapper response=new ResponseWrapper(false,null);
        String data=folderService.deleteSpot(cartDTO);
        if (data!=null){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}
