package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  // 게시글 댓글 목록 불러오기
  List<Reply> getRepliesByBoardOrderByRnoAsc(Board board);
  // 회원이 작성한 댓글 목록 불러오기
  List<Reply> getRepliesByMemberOrderByRegDate(Member member);


}
