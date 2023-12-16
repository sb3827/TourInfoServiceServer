package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    //자식이 몇개있는지 반환
    int countAllByParent(Reply reply);

    //게시글에 해당하는 것 삭제
    void deleteAllByBoard(Board board);
}
