package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.cart.CartService;
import com.dot.tour_info_service_server.service.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 전체 authenticated
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    private final CartService cartService;

    //폴더 내용 모두 들고오기
    @GetMapping(value = "/all/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<List<FolderAllDTO>>> allFolder(@PathVariable Long mno){
        List<FolderAllDTO> data=folderService.getAllFolder(mno);
        ResponseWrapDTO<List<FolderAllDTO>> response=new ResponseWrapDTO<>(true,data);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //폴더명 모두 조회
    @GetMapping(value = "/title/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<List<FolderNameDTO>>>folderNames(@PathVariable Long mno){
        List<FolderNameDTO> data=folderService.getTitle(mno);
        ResponseWrapDTO<List<FolderNameDTO>> response=new ResponseWrapDTO<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //폴더 등록 - valid완료
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<Long>>register(@RequestBody FolderRegistDTO folderRegistDTO){
        ResponseWrapDTO response=new ResponseWrapDTO<>(false,null);
        if(SecurityUtil.validateMno(folderRegistDTO.getMno())) {
            Long data = folderService.register(folderRegistDTO);
            if (data > 0) {
                response.setResult(true);
                response.setData(data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    //폴더명 수정 - valid 완료
    @PutMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<Long>>modify(@RequestBody FolderDTO folderDTO){
        ResponseWrapDTO response=new ResponseWrapDTO<>(false,null);
        if(SecurityUtil.validateMno(folderDTO.getMno())) {
            Long data = folderService.modify(folderDTO);
            if (data != -1l) {
                response.setResult(true);
                response.setData(data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    //폴더 삭제 -service에서 valid 완료
    @DeleteMapping(value = "/delete/{fno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<Long>> remove(@PathVariable Long fno){
        ResponseWrapDTO response = new ResponseWrapDTO(false,null);
        Long data=folderService.remove(fno);
        if (data!=-1l){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    //스팟 등록 -value 완료
    @PostMapping(value = "/cart-append",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<Long>>spotAdd(@RequestBody CartDTO cartDTO){

        ResponseWrapDTO response=new ResponseWrapDTO(false,null);
        if(SecurityUtil.validateMno(cartDTO.getMno())) {
            Long data = cartService.addCart(cartDTO);
            if (data != -1l) {
                response.setResult(true);
                response.setData(data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    //스팟 삭제 - valid완료
    @DeleteMapping(value="/cart-delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapDTO<Long>>spotDelete(@RequestBody CartDTO cartDTO){
        ResponseWrapDTO response=new ResponseWrapDTO(false,null);
        if(SecurityUtil.validateMno(cartDTO.getMno())) {
            Long data = cartService.deleteCart(cartDTO);
            if (data != null) {
                response.setResult(true);
                response.setData(data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

}
