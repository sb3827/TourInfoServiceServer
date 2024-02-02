package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import com.dot.tour_info_service_server.service.place.PlaceService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PlaceServiceImplTests {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;


    // 장소등록 테스트
    @Test
    void register(){
        RegistPlaceRequestDTO dto = RegistPlaceRequestDTO.builder()
                .name("Test1")
                .lat(123.0)
                .lng(123.0)
                .localAddress("test")
                .roadAddress("test")
                .engAddress("test")
                .category(Category.RESTAURANT)
                .build();
        Long pno = placeService.registerPlace(dto);
        System.out.println("pno : " + pno);
    }


    // 장소검색 테스트
    @Test
    void placeSearchTest(){
        log.info(placeService.searchPlace(null, "test", 0).get(0));
    }

    // 마이페이지 방문횟수 테스트
    @Test
    void getPlaceCountTest(){
        log.info(placeService.getPlaceCount(2L));
    }

    // 장소삭제 테스트
    @Test
    void deletePlaceTest(){
        placeService.removePlace(1L);
    }

}