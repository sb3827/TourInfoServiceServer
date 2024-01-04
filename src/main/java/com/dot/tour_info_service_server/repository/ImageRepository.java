package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Image;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

//    @Query("DELETE FROM Board b WHERE b.bno IN (SELECT bp.boardPlacePK.board.bno FROM BoardPlace bp WHERE bp.boardPlacePK.place.pno = :pno)")
//@Query("delete from Reply r where r.board.bno in ( select bp.boardPlacePK.board.bno from BoardPlace bp where bp.boardPlacePK.place.pno = :pno)")

    @Modifying
    @Transactional
    @Query("delete from Image i where i.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno )")
    void removeImage(long pno);

    //게시글에 해당하는 image 삭제
    void deleteAllByBoard(Board board);

  //board 삭제시 해당하는 이미지삭제
  @Modifying // update, delete를 할 때는 무조건 붙인다.
  @Query("delete from Image i where i.board.bno=:bno")
  void deleteByBno(Long bno);


  // bno 넣을때 해당하는 image 반환
  @Transactional
  @Query("SELECT i FROM Image i WHERE i.board.bno=:bno")
  List<Image> selectImageByBno(@Param("bno") Long bno);

  //bno가 null값은 삭제
    @Query("select i from Image i where i.board.bno = null ")
    void deleteByNullBno();

  List<Image> findAllByBoard(Board board);

}
