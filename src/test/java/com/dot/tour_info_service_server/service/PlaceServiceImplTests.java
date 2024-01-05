package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import com.dot.tour_info_service_server.entity.Place;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class PlaceServiceImplTests {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;


    @Test
    void register(){
        PlaceDTO dto = PlaceDTO.builder().name("Test1").lat(123.0).lng(123.0).localAddress("test").roadAddress("test").engAddress("test").category(Category.RESTAURANT).build();
        Long pno = placeService.registerPlace(dto);
        System.out.println("pno : " + pno);
    }


    @Test
    void deletePlaceTest(){
        placeService.removePlace(1L);
    }

    @Test
    void placeSearchTest(){
        log.info(placeService.searchPlace(null, "test").get(0));
    }
}