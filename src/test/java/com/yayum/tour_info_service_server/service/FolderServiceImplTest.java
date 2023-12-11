package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.FolderAllDTO;
import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.entity.Cart;
import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.repository.CartRepository;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        System.out.println(folderAllDTOS);
    }

    //폴더명 조회 테스트
    @Test
    public void testFolderName(){
        Long mno=2l;
        List<String> result=folderRepository.getFolderTitle(mno);
        System.out.println(result);
    }

    //폴더명 수정 테스트
    @Test
    public void testFolderModify(){
        FolderDTO folderDTO=new FolderDTO(1l,2l,"test : modify");
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
        Long fno=1l;
        folderRepository.deleteById(fno);
        System.out.println(fno+" 제거");
    }


}