package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long> {

    //폴더 모두 조회
    @Query("select f.fno,f.title,p.pno,p.name,i.src " +
            "from Folder f " +
            "left outer join Cart c on f.fno=c.cartPK.folder.fno " +
            "left outer join Place p on c.cartPK.place.pno=p.pno " +
            "left outer join BoardPlace bp on bp.place.pno=p.pno " +
            "left outer join Image i on i.board.bno=bp.boardPlacePK.board.bno " +
            "where f.member.mno=:mno " +
            "group by f.fno,p.pno ")
    List<Object[]> getFolderAll(Long mno);


    //폴더명 조회
    @Query("select f.title from Folder f where f.member.mno=:mno")
    List<String> getFolderTitle(Long mno);

}