package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Image;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

  //board 삭제시 해당하는 이미지삭제
  @Modifying // update, delete를 할 때는 무조건 붙인다.
  @Query("delete from Image i where i.board.bno=:bno")
  void deleteByBno(Long bno);


  // bno 넣을때 해당하는 ino 반환
  @Transactional
  @Query("SELECT i FROM Image i WHERE i.board.bno=:bno")
  List<Image> selectImageByBno(@Param("bno") Long bno);


}
