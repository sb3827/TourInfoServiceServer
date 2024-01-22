package com.dot.tour_info_service_server.service.folder;

import com.dot.tour_info_service_server.dto.FolderAllDTO;
import com.dot.tour_info_service_server.dto.FolderDTO;
import com.dot.tour_info_service_server.dto.FolderNameDTO;
import com.dot.tour_info_service_server.dto.FolderRegistDTO;
import com.dot.tour_info_service_server.entity.Folder;
import com.dot.tour_info_service_server.entity.Member;

import java.util.List;

public interface FolderService {

    //폴더 전부 조회
    List<FolderAllDTO> getAllFolder(Long mno);

    //폴더명 조회
    List<FolderNameDTO> getTitle(Long mno);

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
