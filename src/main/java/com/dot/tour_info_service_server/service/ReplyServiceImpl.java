package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.ReplyDTO;
import com.dot.tour_info_service_server.dto.ReplyListDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;
import com.dot.tour_info_service_server.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository replyRepository;

  @Override
  public List<ReplyDTO> getListOfReplyByBoard(Long bno) {
    Board board = Board.builder().bno(bno).build();
    List<Reply> result = replyRepository.getRepliesByBoardOrderByRnoAsc(board);
    return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
  }

  @Override
  public List<ReplyDTO> getListOfReplyByMember(Long mno) {
    Member member = Member.builder().mno(mno).build();
    List<Reply> result = replyRepository.getRepliesByMemberOrderByRegDate(member);
    return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
  }

  @Override
  public List<ReplyListDTO> showReplyList(Long mno) {
    List<Object[]> result = replyRepository.showReplyList(mno);
    List<ReplyListDTO> replyList = new ArrayList<>();

    if(!result.isEmpty()){
      for(Object[] list : result){
        ReplyListDTO replyListDTO = ReplyListDTO.builder()
                .rno((Long)list[0])
                .mno((Long)list[1])
                .bno((Long)list[2])
                .title((String)list[3])
                .text((String)list[4])
                .regdate((LocalDateTime)list[5])
                .build();
        replyList.add(replyListDTO);
      }
      return replyList;
    }
    return null;
  }

  @Override
  public void saveReply(ReplyDTO replyDTO){
    Reply reply = dtoToEntity(replyDTO);
    replyRepository.save(reply);
  }

  @Override
  public void modify(ReplyDTO replyDTO) {
    Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
    if (result.isPresent()) {
      Reply reply = result.get();
      if (replyDTO.getMno()==null){   // 유저댓글삭제시 controller 에서 mno를 setNull함
        reply.changeMember(null);
      }
      reply.changeText(replyDTO.getText());
      replyRepository.save(reply);
    }
  }
}
