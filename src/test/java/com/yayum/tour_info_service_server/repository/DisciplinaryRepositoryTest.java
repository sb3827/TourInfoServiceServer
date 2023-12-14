package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DisciplinaryRepositoryTest {

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Test
    public void testDisciplinary(){
        Long mno=2l;
        System.out.println(disciplinaryRepository.findAllByMemberMno(mno));
    }
}