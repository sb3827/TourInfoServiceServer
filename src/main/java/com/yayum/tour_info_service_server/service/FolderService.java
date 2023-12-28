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



    //Folder dtoToEntity
    default Folder dtoToEntity(FolderDTO folderDTO){
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
    default FolderDTO entityToDto(Folder folder){
        FolderDTO folderDTO=FolderDTO.builder()
                .fno(folder.getFno())
                .mno(folder.getMember().getMno())
                .title(folder.getTitle())
                .build();
        return folderDTO;
    }


}
