package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

  @Modifying // update, delete를 할 때는 무조건 붙인다.
  @Query("delete from Image i where i.board.bno=:bno")
  void deleteByBno(Long bno);
}
