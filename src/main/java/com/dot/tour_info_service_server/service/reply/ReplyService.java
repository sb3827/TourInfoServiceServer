package com.dot.tour_info_service_server.service.reply;


import com.dot.tour_info_service_server.dto.request.reply.ReplyRequestDTO;
import com.dot.tour_info_service_server.dto.response.reply.ReplyListResponseDTO;
import com.dot.tour_info_service_server.dto.response.reply.ReplyMemberResponseDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;

import java.util.List;

public interface ReplyService {
//  List<ReplyDTO> getListOfReplyByBoard(Long bno);    // board 의 댓글목록 불러오기

  //부모 댓글 조회
  List<ReplyMemberResponseDTO> parentReply(Long bno);

  //자식 댓글 조회
  List<ReplyMemberResponseDTO> childReply(Long bno,Long rno);

  List<ReplyRequestDTO> getListOfReplyByMember(Long mno);    // 회원이 작성한 댓글 목록

  List<ReplyListResponseDTO> showReplyList(Long mno);

  void saveReply(ReplyRequestDTO replyRequestDTO);

  void modify(ReplyRequestDTO replyRequestDTO);


  default Reply dtoToEntity(ReplyRequestDTO replyRequestDTO) {
    Reply reply;
    if (replyRequestDTO.getParentRno() != null) {
      reply = Reply.builder()
          .member(Member.builder().mno(replyRequestDTO.getMno()).build())
          .text(replyRequestDTO.getText())
          .board(Board.builder().bno(replyRequestDTO.getBno()).build())
          .parent(Reply.builder().rno(replyRequestDTO.getParentRno()).build())
          .build();
    } else {
      reply = Reply.builder()
          .member(Member.builder().mno(replyRequestDTO.getMno()).build())
          .text(replyRequestDTO.getText())
          .board(Board.builder().bno(replyRequestDTO.getBno()).build())
          .build();
    }
    return reply;
  }

  default ReplyRequestDTO entityToDto(Reply reply) {
    ReplyRequestDTO replyRequestDTO;
    if (reply.getMember() != null) {
      if (reply.getParent() != null && reply.getParent().getRno() != null) {
        replyRequestDTO = ReplyRequestDTO.builder()
            .rno(reply.getRno())
            .text(reply.getText())
            .bno(reply.getBoard().getBno())
            .mno(reply.getMember().getMno())
            .parentRno(reply.getParent().getRno())
            .regDate(reply.getRegDate())
            .modDate(reply.getModDate())
            .build();
      } else {
        replyRequestDTO = ReplyRequestDTO.builder()
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
      replyRequestDTO = ReplyRequestDTO.builder()
          .rno(reply.getRno())
          .text(reply.getText())
          .bno(reply.getBoard().getBno())
          .regDate(reply.getRegDate())
          .modDate(reply.getModDate())
          .build();
    }
    return replyRequestDTO;
  }

}
