package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {
}
