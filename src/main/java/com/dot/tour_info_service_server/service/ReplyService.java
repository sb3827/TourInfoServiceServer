package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.ReplyDTO;
import com.dot.tour_info_service_server.dto.ReplyListDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;

import java.util.List;

public interface ReplyService {
  List<ReplyDTO> getListOfReplyByBoard(Long bno);    // board 의 댓글목록 불러오기

  List<ReplyDTO> getListOfReplyByMember(Long mno);    // 회원이 작성한 댓글 목록

  List<ReplyListDTO> showReplyList(Long mno);

  void saveReply(ReplyDTO replyDTO);

  void modify(ReplyDTO replyDTO);


  default Reply dtoToEntity(ReplyDTO replyDTO) {
    Reply reply;
    if (replyDTO.getParentRno() != null) {
      reply = Reply.builder()
          .member(Member.builder().mno(replyDTO.getMno()).build())
          .text(replyDTO.getText())
          .board(Board.builder().bno(replyDTO.getBno()).build())
          .parent(Reply.builder().rno(replyDTO.getParentRno()).build())
          .build();
    } else {
      reply = Reply.builder()
          .member(Member.builder().mno(replyDTO.getMno()).build())
          .text(replyDTO.getText())
          .board(Board.builder().bno(replyDTO.getBno()).build())
          .build();
    }
    return reply;
  }

  default ReplyDTO entityToDto(Reply reply) {
    ReplyDTO replyDTO;
    if (reply.getMember() != null) {
      if (reply.getParent() != null && reply.getParent().getRno() != null) {
        replyDTO = ReplyDTO.builder()
            .rno(reply.getRno())
            .text(reply.getText())
            .bno(reply.getBoard().getBno())
            .mno(reply.getMember().getMno())
            .parentRno(reply.getParent().getRno())
            .regDate(reply.getRegDate())
            .modDate(reply.getModDate())
            .build();
      } else {
        replyDTO = ReplyDTO.builder()
            .rno(reply.getRno())
            .text(reply.getText())
            .bno(reply.getBoard().getBno())
            .mno(reply.getMember().getMno())
            .regDate(reply.getRegDate())
            .modDate(reply.getModDate())
            .build();
      }
    } else {
      // 유저가 삭제한 댓글에 의해 mno가 null인 경우에 대한 처리
      replyDTO = ReplyDTO.builder()
          .rno(reply.getRno())
          .text(reply.getText())
          .bno(reply.getBoard().getBno())
          .regDate(reply.getRegDate())
          .modDate(reply.getModDate())
          .build();
    }
    return replyDTO;
  }

}
