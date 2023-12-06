package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.FolderChangeDTO;
import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.dto.FolderDeleteDTO;
import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.entity.Member;

import java.util.List;

public interface FolderService {

    //폴더 내용 조회
    List<FolderDTO> getAllFolder(Long mno);

    //폴더명 조회
    List<String> getTitle(Long mno);

    //폴더 등록
    Long register(FolderDTO folderDTO);

    //폴더명 수정 - 성공 시 폴더번호, 실패시 -1
    Long modify(FolderChangeDTO folderChangeDTO);

    //폴더 삭제 - 성공 시 폴더번호, 실패시 -1
    Long remove(FolderDeleteDTO folderDeleteDTO);

    //장바구니 스팟 추가 - 추후 진행

    //장바구니 스팟 삭제 - 추후 진행


    //dtoToEntity
    default Folder dtoToEntity(FolderDTO folderDTO){
        Folder folder=Folder.builder()
                .fno(folderDTO.getFno())
                .mno(Member
                        .builder()
                        .mno(folderDTO.getMno())
                        .build())
                .title(folderDTO.getTitle())
                .build();
        return folder;
    }

    //entityToDto
    default FolderDTO entityToDto(Folder folder){
        FolderDTO folderDTO=FolderDTO.builder()
                .fno(folder.getFno())
                .mno(folder.getMno().getMno())
                .title(folder.getTitle())
                .build();
        return folderDTO;
    }
}
