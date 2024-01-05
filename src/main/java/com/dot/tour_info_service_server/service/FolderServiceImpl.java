package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.FolderAllDTO;
import com.dot.tour_info_service_server.dto.FolderDTO;
import com.dot.tour_info_service_server.dto.FolderNameDTO;
import com.dot.tour_info_service_server.dto.FolderRegistDTO;
import com.dot.tour_info_service_server.entity.Folder;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.repository.FolderRepository;
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
            String src=(String)objects[4];

            FolderAllDTO folderAllDTO = folderMap.computeIfAbsent(fno, k -> FolderAllDTO.builder().fno(fno).title(title).pno(new ArrayList<>()).name(new ArrayList<>()).src(new ArrayList<>()).build());
            folderAllDTO.getPno().add(pno);
            folderAllDTO.getName().add(name);
            folderAllDTO.getSrc().add(src);
        }

        return new ArrayList<>(folderMap.values());
    }

    //폴더명 조회
    @Override
    public List<FolderNameDTO> getTitle(Long mno) {
        List<Folder> result=folderRepository.getFolderTitle(mno);
        List<FolderNameDTO>folderNameDTOS=new ArrayList<>();
        for (Folder folder:result){
            FolderNameDTO folderNameDTO=FolderNameDTO.builder()
                    .fno(folder.getFno())
                    .title(folder.getTitle())
                    .build();
            folderNameDTOS.add(folderNameDTO);
        }
        return folderNameDTOS;
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
