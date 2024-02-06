package com.dot.tour_info_service_server.service.reply;

import com.dot.tour_info_service_server.dto.ReplyListDTO;
import com.dot.tour_info_service_server.dto.ReplyMemberDTO;
import com.dot.tour_info_service_server.dto.request.reply.ReplyDeleteRequestDTO;
import com.dot.tour_info_service_server.dto.request.reply.ReplyRequestDTO;
import com.dot.tour_info_service_server.dto.request.reply.ReplyUpdateRequestDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;

import java.util.List;

public interface ReplyService {
  //부모 댓글 조회
  List<ReplyMemberDTO> parentReply(Long bno);

  //자식 댓글 조회
  List<ReplyMemberDTO> childReply(Long bno,Long rno);

  List<ReplyListDTO> showReplyList(Long mno);

  void saveReply(ReplyRequestDTO replyDTO);

  void modify(ReplyUpdateRequestDTO replyUpdateRequestDTO);

  void delete(ReplyDeleteRequestDTO replyDeleteRequestDTO);


  default Reply dtoToEntity(ReplyRequestDTO replyDTO) {
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
}
