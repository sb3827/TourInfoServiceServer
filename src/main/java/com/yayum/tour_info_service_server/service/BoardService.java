package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;

import java.util.Map;

public interface BoardService {

  Long register(BoardDTO boardDTO);

  Long removeWithReplies(Long bno);

  void modify(BoardDTO boardDTO);

  default Board dtoToEntity (BoardDTO boardDTO) {
    Board board = Board.builder()
        .bno(boardDTO.getBno())
        .title(boardDTO.getTitle())
        .content(boardDTO.getContent())
        .isAd(boardDTO.getIsAd())
        .isCourse(boardDTO.getIsCourse())
        .score(boardDTO.getScore())
        .likes(boardDTO.getLikes())
        .writer(Member.builder().mno(boardDTO.getWriter()).build())
        .build();
    return board;
  }

  default BoardDTO entityToDto (Board board) {
    BoardDTO boardDTO = BoardDTO.builder()
        .bno(board.getBno())
        .title(board.getTitle())
        .content(board.getContent())
        .isAd(board.getIsAd())
        .isCourse(board.getIsCourse())
        .content(board.getContent())
        .score(board.getScore())
        .likes(board.getLikes())
        .modDate(board.getModDate())
        .regDate(board.getRegDate())
        .build();
    return boardDTO;
  }

}
