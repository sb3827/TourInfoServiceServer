package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.entity.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

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
        IntStream.rangeClosed(1, 25).forEach(i -> {
            Place place = Place.builder().name("test"+ i).lng(300.0).lat(300.0).category(Category.valueOf("ETC")).localAddress("test" + i).roadAddress("test" + i).engAddress("test" + i).cart(i).build();
            placeRepository.save(place);
        });
    }

    @Test
    public void findPlaceTest(){
        List<Place> list = placeRepository.findPlace(Category.RESTAURANT, "test");
        for(Place place : list){
            System.out.println(place);
        }
    }

}