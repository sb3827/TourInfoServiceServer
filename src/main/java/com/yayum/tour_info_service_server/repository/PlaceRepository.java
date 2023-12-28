package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Category;
import com.yayum.tour_info_service_server.entity.Place;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    // 장소 검색
    @Query("select p from Place p where (p.name like %:search% or p.localAddress like %:search% or p.roadAddress like %:search% or p.engAddress like %:search%) and " +
            "(p.category = :filter or :filter = com.yayum.tour_info_service_server.entity.Category.ETC)")
    List<Place> findPlace(Category filter, String search);

    //게시글이 가장 많은 장소 3곳 정보
    @Query("select p.pno, p.name,i.src " +
        "from Place p left outer join BoardPlace bp on (bp.place.pno=p.pno) " +
        "left outer join Board b on (b.bno=bp.boardPlacePK.board.bno)" +
        "left outer join Image i on(b.bno=i.board.bno) " +
        "group by p.pno " +
        "order by count (b)desc " +
        "limit 3")
    List<Object[]> mostLikePlace();
}
