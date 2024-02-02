package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Disciplinary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DisciplinaryRepositoryTest {

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Test
    public void testDisciplinary(){
        Long mno=2l;
        List<Disciplinary> result=disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(mno);
        System.out.println(result.size());
    }

    @Test
    void setNullTest(){
        disciplinaryRepository.setNullMno(16L);
    }
}