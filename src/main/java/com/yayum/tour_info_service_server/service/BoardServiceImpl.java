package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

  private final BoardRepository boardRepository;

  private final ReplyRepository replyRepository;

  private final ImageRepository imageRepository;

  private final ReportRepository reportRepository;

  private final BoardPlaceRepository boardPlaceRepository;

  private final BoardLikeRepository boardLikeRepository;

  @Override
  public Long register(BoardDTO boardDTO) {
    Board board = dtoToEntity(boardDTO);
    log.info("board: ....."+board);
    boardRepository.save(board);
    return board.getBno();
  }

  @Transactional
  @Override
  public Long remove(Long bno) {
    replyRepository.deleteByBno(bno);
    imageRepository.deleteByBno(bno);
    boardLikeRepository.deleteByBno(bno);
    boardPlaceRepository.deleteByBno(bno);
    reportRepository.updateReportByBoardBno(bno); //null값으로 셋팅
    Board board = boardRepository.findById(bno)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 id."));
    boardRepository.deleteById(bno);
    return board.getBno();
  }

  @Override
  @Transactional
  public Long modify(BoardDTO boardDTO) {
    Optional<Board> result = boardRepository.findById(boardDTO.getBno());
    if (result.isPresent()) {
      Board board = result.get();
      log.info("board:"+board);
      board.changeTitle(boardDTO.getTitle());
      board.changeContent(boardDTO.getContent());
      boardRepository.save(board);
      return board.getBno();
    }
      return -1l;
    }

  @Override
  public BoardDTO getPlaceBoardByBno(Long bno) {
    List<Object[]> result = boardRepository.getPlaceBoardByBno(bno);
    // board
    Board board = (Board) result.get(0)[0];
    Member member = (Member) result.get(0)[1];
    return entityToDto(board, member);
  }
}
