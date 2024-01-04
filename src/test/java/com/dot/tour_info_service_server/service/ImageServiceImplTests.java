package com.dot.tour_info_service_server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageServiceImplTests {

    @Autowired
    ImageService imageService;

    @Test
    public void test() {
        imageService.ImageUpload( "dfesef");

    }


}