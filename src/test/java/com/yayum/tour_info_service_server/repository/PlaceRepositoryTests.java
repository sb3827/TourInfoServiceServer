package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Category;
import com.yayum.tour_info_service_server.entity.Place;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static com.yayum.tour_info_service_server.entity.Category.ETC;
import static com.yayum.tour_info_service_server.entity.Category.RESTAURANT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlaceRepositoryTests {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void testClass(){
        System.out.println(placeRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(10, 20).forEach(i -> {
            Place place = Place.builder().name("test"+ i).lng(300.0).lat(300.0).category(Category.valueOf("ETC")).localAddress("test" + i).roadAddress("test" + i).engAddress("test" + i).cart(i).build();
            placeRepository.save(place);
        });
    }

    @Test
    public void findPlaceTest(){
        List<Place> list = placeRepository.findPlace(RESTAURANT, "test");
        for(Place place : list){
            System.out.println(place);
        }
    }

}