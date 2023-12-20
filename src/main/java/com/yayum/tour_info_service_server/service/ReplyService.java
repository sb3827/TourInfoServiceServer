package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.ReplyDTO;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;

import java.util.List;

public interface ReplyService {
  List<ReplyDTO> getListOfReplyByBoard(Long bno);    // board 의 댓글목록 불러오기

  List<ReplyDTO> getListOfReplyByMember(Long mno);    // 회원이 작성한 댓글 목록


  void saveReply(ReplyDTO replyDTO);

  void modify(ReplyDTO replyDTO);

  void delete(ReplyDTO replyDTO);

  void report(Long rno);

  default Reply dtoToEntity(ReplyDTO replyDTO) {
    Reply reply = Reply.builder()
        .member(Member.builder().mno(replyDTO.getMno()).build())
        .text(replyDTO.getText())
        .board(Board.builder().bno(replyDTO.getBno()).build())
        .parent(Reply.builder().rno(replyDTO.getRno()).build())
        .rno(replyDTO.getRno())
        .build();
    return reply;
  }

  default ReplyDTO entityToDto(Reply reply) {
    ReplyDTO replyDTO = ReplyDTO.builder()
        .name(reply.getMember().getName())
        .rno(reply.getRno())
        .text(reply.getText())
        .bno(reply.getBoard().getBno())
        .regDate(reply.getRegDate())
        .modDate(reply.getModDate())
        .build();
    return replyDTO;
  }
}
