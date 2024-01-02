package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.ReplyDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;
import com.dot.tour_info_service_server.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
