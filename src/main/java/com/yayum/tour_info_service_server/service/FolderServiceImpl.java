package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.CartRepository;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import com.yayum.tour_info_service_server.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class FolderServiceImpl implements FolderService{

    //폴더 repository
    private final FolderRepository folderRepository;

    //폴더 전부 조회
    @Override
    public List<FolderAllDTO> getAllFolder(Long mno) {
        List<Object[]> result = folderRepository.getFolderAll(mno);
        Map<Long, FolderAllDTO> folderMap = new HashMap<>();

        for (Object[] objects : result) {
            Long fno = (Long) objects[0];
            String title = (String) objects[1];
            Long pno = (Long) objects[2];
            String name = (String) objects[3];

            FolderAllDTO folderAllDTO = folderMap.computeIfAbsent(fno, k -> FolderAllDTO.builder().fno(fno).title(title).pno(new ArrayList<>()).name(new ArrayList<>()).build());
            folderAllDTO.getPno().add(pno);
            folderAllDTO.getName().add(name);
        }

        return new ArrayList<>(folderMap.values());
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
