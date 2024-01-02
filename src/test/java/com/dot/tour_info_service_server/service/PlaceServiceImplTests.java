package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import com.dot.tour_info_service_server.entity.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PlaceServiceImplTests {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;


    @Test
    void register(){
        PlaceDTO dto = PlaceDTO.builder().name("Test1").lat(123.0).lng(123.0).localAddress("test").roadAddress("test").engAddress("test").category("음식점").build();
        Long pno = placeService.registerPlace(dto);
        System.out.println("pno : " + pno);
    }

    @Test
    void find(){
        List<Place> result = placeRepository.findPlace(Category.RESTAURANT, "test");
        for(Place place: result){
            System.out.println(place);
        }
    }

    @Test
    void deletePlaceTest(){
        placeService.removePlace(1L);
    }
}