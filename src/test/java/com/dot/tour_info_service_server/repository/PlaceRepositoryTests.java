package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.entity.Place;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PlaceRepositoryTests {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void testClass(){
        System.out.println(placeRepository.getClass().getName());
    }

    // Place 더미 데이터 생성
    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(4, 5).forEach(i -> {
            Place place = Place.builder().name("test"+ i).lng(300.0).lat(300.0).category(Category.valueOf("RESTAURANT")).localAddress("test" + i).roadAddress("test" + i).engAddress("test" + i).cart(i).build();
            placeRepository.save(place);
        });
    }

    // 장소 검색 테스트
    @Test
    public void placeWithImageTest(){
        Object[] result = placeRepository.searchPlace(null, "test").get(0);
        for(Object place: result){
            log.info(place);
        }
    }

    // 마이페이지 지도를 위한 프로시저 실행 테스트
    @Test
    @Transactional
    public void getPlaceCountTest(){
        List<Object[]> result = placeRepository.getPlaceCount(2L);
        for(Object[] list: result){
            for(Object count:list){
                log.info("결과 : " + count);
            }
        }
    }
}