package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long> {

    //폴더 모두 조회 - 오류
    @Query("select f.fno,f.title,p.pno,p.name " +
            "from Folder f " +
            "left outer join Cart c on f.fno=c.cartPK.folder.fno " +
            "left outer join Place p on c.cartPK.place.pno=p.pno " +
            "where f.member.mno=:mno ")
    List<Object[]> getFolderAll(Long mno);

    //폴더명 조회
    @Query("select f.title from Folder f where f.member.mno=:mno")
    List<String> getFolderTitle(Long mno);

}