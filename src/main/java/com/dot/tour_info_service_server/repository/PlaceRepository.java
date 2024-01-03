package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 장소 검색
    @Query("select p from Place p where " +
            "(:filter = null or p.category = :filter) and " +
            "(p.name like %:search% or p.localAddress like %:search% or " +
            "p.roadAddress like %:search% or p.engAddress like %:search%)")
    List<Place> findPlace(Category filter, String search);

    @Query("select p.pno, p.name, p.lng, p.lat, p.roadAddress, p.localAddress, p.engAddress, p.category, p.cart, p.regDate, p.modDate" +
            " from Place p  where :filter is null or p.category = :filter and " +
            "(p.name like %:search% or p.localAddress like %:search% or " +
            "p.roadAddress like %:search% or p.engAddress like %:search%)")
    List<Object[]> searchPlace(Category filter, String search);
}
