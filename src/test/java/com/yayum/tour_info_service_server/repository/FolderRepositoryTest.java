package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class FolderRepositoryTest {
    @Autowired
    private FolderRepository folderRepository;



    //폴더 생성 테스트
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,10).forEach(i->{
            Folder folder=Folder.builder()
                    .member(Member.builder().mno(21l).build())
                    .title("test : "+i)
                    .build();
            folderRepository.save(folder);
        });
    }

    //폴더 전부 조회
    @Test
    public void listAll(){
        List<Object[]> result=folderRepository.getFolderAll(1l);
        for (Object[] item : result) {
            System.out.println("item2222 : "+ Arrays.toString(item));
        }
    }

    //폴더명 조회
    @Test
    public void getTitle(){
        List<String>result=folderRepository.getFolderTitle(1l);
        System.out.println(result);
    }
}