package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    //게시글이 가장 많은 장소 3곳 정보
    @Query("select p.pno, p.name,i.src " +
            "from Place p left outer join BoardPlace bp on (bp.place.pno=p.pno) " +
            "left outer join Board b on (b.bno=bp.boardPlacePK.board.bno)" +
            "left outer join Image i on(b.bno=i.board.bno) " +
            "group by p.pno " +
            "order by count (b)desc " +
            "limit 3")
    List<Object[]> mostLikePlace();


    @Query("select p.pno, p.name, p.lng, p.lat, p.roadAddress, p.localAddress, p.engAddress, p.category, p.cart, p.regDate, p.modDate, i.src " +
            "from Place p left outer join BoardPlace bp on p.pno = bp.place.pno " +
            "left outer join Image i on bp.boardPlacePK.board.bno = i.board.bno " +
            "where :filter is null and " +
            "(p.name like %:search% or p.localAddress like %:search% or " +
            "p.roadAddress like %:search% or p.engAddress like %:search%) " +
            "or p.category = :filter and " +
            "(p.name like %:search% or p.localAddress like %:search% or " +
            "p.roadAddress like %:search% or p.engAddress like %:search%) " +
            "group by p.pno")
    List<Object[]> searchPlace(Category filter, String search);



    // 보드에 해당하는 place 반환
    @Query("select p.pno, p.name, p.category, p.lng, p.lat, p.roadAddress, p.localAddress, " +
            "p.engAddress,  p.cart ,p.regDate, p.modDate from Place p " +
            "left outer join BoardPlace bp on bp.place.pno= p.pno " +
            "left outer join Board b on b.bno = bp.boardPlacePK.board.bno " +
            "where b.bno =:bno ")
    List<Object[]> getPlaceByBoard(Long bno);
}
