package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  @Modifying
  @Transactional
  // 보드 테이블 bno가 boardplace의 board_bno 와 같은거 삭제
  @Query("delete from Reply r where r.board.bno in ( select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
  void removeReply(Long pno);


  @Modifying
  @Transactional
  @Query("DELETE FROM Reply r WHERE r.parent.rno IN (SELECT r.rno FROM Reply r WHERE r.board.bno IN (SELECT bp.boardPlacePK.board.bno FROM BoardPlace bp WHERE bp.place.pno = :pno))")
    //    @Query("delete from Reply r where r.rno in ( select r.parent from Reply r where r.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno))")
  void removeChildReply(Long pno);


  //자식이 몇개있는지 반환
  int countAllByParent(Reply reply);

  //게시글에 해당하는 것 삭제
  void deleteAllByBoard(Board board);

  // 게시글 댓글 목록 불러오기
  List<Reply> getRepliesByBoardOrderByRnoAsc(Board board);

  // 회원이 작성한 댓글 목록 불러오기
  List<Reply> getRepliesByMemberOrderByRegDate(Member member);


}
