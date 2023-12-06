package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Folder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long> {

    //폴더 모두 조회


    //폴더명 조회
    @EntityGraph(attributePaths = "mno",type = EntityGraph.EntityGraphType.LOAD)
    @Query("select f.title from Folder f where f.mno=:mno")
    List<String> getFolderTitle(Long mno);


    //폴더 - mno와 title 넘겨서 해당하는 컬럼 가져오기
    Folder findByMnoAndTitle(Long mno,String title);

    //폴더에 스팟 추가

    //폴더에 스팟 제거

}