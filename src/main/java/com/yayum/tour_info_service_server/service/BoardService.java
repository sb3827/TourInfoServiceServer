package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Image;
import com.yayum.tour_info_service_server.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BoardService {

  // 등록
  Long register(BoardDTO boardDTO);

  // 삭제
  Long remove(Long bno);

  // 수정 성공시 bno 실패시 -1
  Long modify(BoardDTO boardDTO);

//  // 장소 포스팅 정보 조회 실패시, null 반환
//  BoardDTO getPlaceBoardByBno(Long bno);
//
//  // 코스 포스팅 정보 조회 실패시, null 반환
//  BoardDTO getCourseBoardByBno(Long bno);

  // 회원별 포스팅 정보 조회, 실패시 null 반환
  //  List<BoardDTO> getBoardByMno(Long mno);


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
        .writer(board.getWriter().getMno())
        .modDate(board.getModDate())
        .regDate(board.getRegDate())
        .build();
    return boardDTO;
  }

}
