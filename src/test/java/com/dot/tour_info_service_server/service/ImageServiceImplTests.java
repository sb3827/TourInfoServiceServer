package com.dot.tour_info_service_server.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class ImageServiceImplTests {

    @Autowired
    ImageService imageService;

    @Test
    public void test() {
        imageService.ImageUpload( "dfesef");

    }




}