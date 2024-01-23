package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.request.folder.FolderAllRequestDTO;
import com.dot.tour_info_service_server.dto.response.folder.FolderAllResponseDTO;
import com.dot.tour_info_service_server.dto.response.folder.FolderNameResponseDTO;
import com.dot.tour_info_service_server.entity.Folder;
import com.dot.tour_info_service_server.repository.CartRepository;
import com.dot.tour_info_service_server.repository.FolderRepository;
import com.dot.tour_info_service_server.service.folder.FolderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class FolderServiceImplTest{
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FolderService folderService;

    //폴더 전체 조회 테스트
    @Test
    public void testGetAllFolder() {
        Long mno=2l;
        List<Object[]> result = folderRepository.getFolderAll(mno);
        Map<Long, FolderAllResponseDTO> folderMap = new HashMap<>();

        for (Object[] objects : result) {
            Long fno = (Long) objects[0];
            String title = (String) objects[1];
            Long pno = (Long) objects[2];
            String name = (String) objects[3];

            FolderAllResponseDTO folderAllDTO = folderMap.computeIfAbsent(fno, k -> FolderAllResponseDTO.builder().fno(fno).title(title).pno(new ArrayList<>()).name(new ArrayList<>()).build());
            folderAllDTO.getPno().add(pno);
            folderAllDTO.getName().add(name);
        }
        System.out.println(new ArrayList<>(folderMap.values()));
    }

    //폴더명 조회 테스트
    @Test
    public void testFolderName(){
        Long mno=2l;
        List<Folder> result=folderRepository.getFolderTitle(mno);
        for (Folder folder:result){
            FolderNameResponseDTO folderNameDTO=FolderNameResponseDTO.builder()
                    .fno(folder.getFno())
                    .title(folder.getTitle())
                    .build();
            System.out.println(folderNameDTO);
        }
    }

    //폴더명 수정 테스트
    @Test
    public void testFolderModify(){
        FolderAllRequestDTO folderDTO=new FolderAllRequestDTO(1l,2l,"test : modify");
        Long num=folderDTO.getFno();
        Optional<Folder> result=folderRepository.findById(num);
        System.out.println("폴더 "+folderDTO.getTitle()+"로 수정");
        if(result.isPresent()) {
            Folder folder=result.get();
            folder.changeTitle(folderDTO.getTitle());
            folderRepository.save(folder);
        }
    }

    //폴더 삭제 테스트
    @Test
    public void testFolderDelete(){
        folderService.remove(583l);
    }


}