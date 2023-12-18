package com.yayum.tour_info_service_server.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;

import java.util.List;

public interface FolderService {

    //폴더 전부 조회
    List<FolderAllDTO> getAllFolder(Long mno);

    //폴더명 조회
    List<String> getTitle(Long mno);

    //폴더 등록
    Long register(FolderRegistDTO folderRegistDTO);

    //폴더명 수정 - 성공 시 폴더번호, 실패시 -1
    Long modify(FolderDTO folderDTO);

    //폴더 삭제 - 성공 시 폴더번호, 실패시 -1
    Long remove(Long fno);

    //장바구니 스팟 추가 - 추후 진행
    Long addSpot(CartDTO cartDTO);

    //장바구니 스팟 삭제 - 추후 진행
    Long deleteSpot(CartDTO cartDTO);

    //Folder dtoToEntity
    default Folder folderDtoToEntity(FolderDTO folderDTO){
        Folder folder=Folder.builder()
                .fno(folderDTO.getFno())
                .member(Member
                        .builder()
                        .mno(folderDTO.getMno())
                        .build())
                .title(folderDTO.getTitle())
                .build();
        return folder;
    }

    //Folder entityToDto
    default FolderDTO folderEntityToDto(Folder folder){
        FolderDTO folderDTO=FolderDTO.builder()
                .fno(folder.getFno())
                .mno(folder.getMember().getMno())
                .title(folder.getTitle())
                .build();
        return folderDTO;
    }

    //Cart dtoToEntity
    default Cart cartDtoToEntity(CartDTO cartDTO){
        CartPK cartPK=CartPK.builder()
                .member(Member.builder().mno(cartDTO.getMno()).build())
                .place(Place.builder().pno(cartDTO.getPno()).build())
                .folder(Folder.builder().fno(cartDTO.getFno()).build())
                .build();
        Cart cart=Cart.builder()
                .cartPK(cartPK)
                .build();

        return cart;
    }

    //Cart entityToDTO
    default CartDTO cartEntityToDto(Cart cart){
        CartDTO cartDTO=CartDTO.builder()
                .mno(cart.getCartPK().getMember().getMno())
                .pno(cart.getCartPK().getPlace().getPno())
                .fno(cart.getCartPK().getFolder().getFno())
                .build();
        return cartDTO;
    }
}
