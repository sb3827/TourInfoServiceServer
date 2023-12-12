package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testAllReport(){
        List<Report> result=reportRepository.findAllByOrderByRegDateDesc();
        System.out.println(result);
    }

}