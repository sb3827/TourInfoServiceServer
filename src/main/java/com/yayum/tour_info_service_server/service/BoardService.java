package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {

    // 장소 등록
    Long placeRegister(PlaceBoardDTO placeBoardDTO);

    // 코스 등록
    Long courseRegister(CourseBoardDTO courseBoardDTO);

    // 삭제
    Long remove(Long bno);

    // 장소 수정
    Long placeBoardModify(PlaceBoardDTO placeBoardDTO);

    // 코스 수정
    Long modifyCourse(CourseBoardDTO courseBoardDTO) throws IllegalAccessException, SQLException;

    // 장소 포스팅 정보 조회
    BoardDTO getBoardByBno(Long bno);

    // 메인 포스팅 조회
    MainResponseDTO mainBoard(Long mno);

    // 회원별 장소 포스팅 정보 조회, 실패시 null 반환
    List<BoardReplyCountDTO> getBoardByMno(Long mno);

    // 장소별 장소 포스팅 정보 조회
    List<BoardPlaceReplyCountDTO> getBoardByPno(Long pno);

    // 회원별 코스 포스팅 정보 조회
    List<BoardReplyCountDTO> getCourseBoardByMno(Long mno);


    default Board placeDtoToEntity(PlaceBoardDTO placeBoardDTO) {
        Board board = Board.builder()
//        .bno(boardDTO.getBno())
                .title(placeBoardDTO.getTitle())
                .content(placeBoardDTO.getContent())
                .isAd(placeBoardDTO.getIsAd())
                .isCourse(placeBoardDTO.getIsCourse())
                .score(placeBoardDTO.getScore())
                .likes(placeBoardDTO.getLikes())
                .writer(Member.builder().mno(placeBoardDTO.getWriter()).build())
                .build();
        return board;
    }

    default Board courseDtoToEntity(CourseBoardDTO courseBoardDTO) {
        Board board = Board.builder()
//        .bno(courseBoardDTO.getBno())
                .title(courseBoardDTO.getTitle())
                .content(courseBoardDTO.getContent())
                .isAd(courseBoardDTO.getIsAd())
                .isCourse(courseBoardDTO.getIsCourse())
                .score(courseBoardDTO.getScore())
                .likes(courseBoardDTO.getLikes())
                .writer(Member.builder().mno(courseBoardDTO.getWriter()).build())
                .build();
        return board;
    }

    default BoardDTO entityToDto(Board board) {
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
