package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {


//    @Query("DELETE FROM Board b WHERE b.bno IN (SELECT bp.boardPlacePK.board.bno FROM BoardPlace bp WHERE bp.boardPlacePK.place.pno = :pno)")
//@Query("delete from Reply r where r.board.bno in ( select bp.boardPlacePK.board.bno from BoardPlace bp where bp.boardPlacePK.place.pno = :pno)")

    @Modifying
    @Transactional
    @Query("delete from Image i where i.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno )")
    void removeImage(long pno);
}
