package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.repository.DisciplinaryRepository;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReportServiceImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;
}