package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  //bno를 삭제했을때 해당 rno가 삭제됨
  @Modifying // update, delete를 할 때는 무조건 붙인다.
  @Query("delete from Reply r where r.board.bno=:bno")
  void deleteByBno(Long bno);

//  bno를 삭제했을때 해당 댓글의 대댓글이 삭제됨
  @Modifying
  @Query("DELETE FROM Reply r WHERE r.board.bno =:bno AND r.parent.rno is not null")
  void deleteByChildRno(Long bno);

}
