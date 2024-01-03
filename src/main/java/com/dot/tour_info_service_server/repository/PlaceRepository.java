package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 장소 검색
    @Query("select p from Place p where " +
            "(:filter = 'ALL' or p.category = :filter) and " +
            "(p.name like %:search% or p.localAddress like %:search% or " +
            "p.roadAddress like %:search% or p.engAddress like %:search%)")
    List<Place> findPlace(Category filter, String search);
}
