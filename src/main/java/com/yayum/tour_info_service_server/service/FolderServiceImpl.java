package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.CartRepository;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import com.yayum.tour_info_service_server.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class FolderServiceImpl implements FolderService{

    //폴더 repository
    private final FolderRepository folderRepository;

    //폴더 전부 조회
    @Override
    public List<FolderAllDTO> getAllFolder(Long mno) {
        List<Object[]> result=folderRepository.getFolderAll(mno);
        List<FolderAllDTO> folderAllDTOS = new ArrayList<>();

        for (Object[] objects : result) {
            FolderAllDTO folderAllDTO = new FolderAllDTO();
            folderAllDTO.setFno((Long) objects[0]);
            folderAllDTO.setTitle((String) objects[1]);
            folderAllDTO.setPno((Long)objects[2]);
            folderAllDTO.setName((String)objects[3]);
            folderAllDTOS.add(folderAllDTO);
        }
        return folderAllDTOS;
    }

    //폴더명 조회
    @Override
    public List<String> getTitle(Long mno) {
        List<String> result=folderRepository.getFolderTitle(mno);
        return result;
    }

    //폴더 등록
    @Override
    public Long register(FolderRegistDTO folderRegistDTO) {
        Folder folder=Folder.builder()
                .member(Member.builder().mno(folderRegistDTO.getMno()).build())
                .title(folderRegistDTO.getTitle())
                .build();
        folderRepository.save(folder);
        return folder.getFno();
    }

    //폴더 수정
    @Override
    public Long modify(FolderDTO folderDTO) {
        Long num=folderDTO.getFno();
        Optional<Folder> result=folderRepository.findById(num);
        if(result.isPresent()) {
            Folder folder=result.get();
            folder.changeTitle(folderDTO.getTitle());
            folderRepository.save(folder);
            return folder.getFno();
        }
        return -1l;
    }

    //폴더 삭제
    @Override
    public Long remove(Long fno) {
        if (folderRepository.findById(fno).isPresent()) {
            folderRepository.deleteById(fno);
            return fno;
        }
        return -1l;
    }

}
