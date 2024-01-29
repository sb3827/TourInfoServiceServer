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

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(4, 5).forEach(i -> {
            Place place = Place.builder().name("test"+ i).lng(300.0).lat(300.0).category(Category.valueOf("RESTAURANT")).localAddress("test" + i).roadAddress("test" + i).engAddress("test" + i).cart(i).build();
            placeRepository.save(place);
        });
    }

    @Test
    public void placeWithImageTest(){
        log.info("cart : " + placeRepository.searchPlace(null, "test").get(0)[8]);
        log.info("cart : " + placeRepository.searchPlace(null, "test").get(2)[8]);
        log.info("image src : " + placeRepository.searchPlace(null, "test").get(0)[11]);

    }

    // 프로시저 실행 테스트
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

    //게시글이 가장 많은 장소 3곳 정보
    @Test
    public void mostLikePlaceTest() {
        List<Object[]> result = placeRepository.mostLikePlace();
        for(Object[] list: result){
            System.out.println(Arrays.toString(list));
        }
    }

}