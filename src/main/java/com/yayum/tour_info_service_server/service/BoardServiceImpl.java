package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.dto.CourseBoardDTO;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
  @Transactional
  public Long placeRegister(BoardDTO boardDTO) {
    Board board = dtoToEntity(boardDTO);
    boardRepository.save(board);
    log.info("board 저장");
    BoardPlacePK boardPlacePK=BoardPlacePK.builder()
        .day(1)
        .orderNumber(1)
        .board(board)
        .build();
    BoardPlace boardPlace=BoardPlace.builder()
        .boardPlacePK(boardPlacePK)
        .place(Place.builder().pno(boardDTO.getPno()).build())
        .build();
    boardPlaceRepository.save(boardPlace);
    if (boardDTO.getSrc()!=null) {
      for (String src : boardDTO.getSrc()) {
        Image image = Image.builder()
            .src(src)
            .board(board)
            .build();
        imageRepository.save(image);
        log.info("image 저장");
      }
    }
    return board.getBno();
  }

  @Override
  @Transactional
  public Long courseRegister(CourseBoardDTO courseBoardDTO) {
    Board board = courseDtoToEntity(courseBoardDTO);
    boardRepository.save(board);
    log.info("board 저장");

    for (Long pno : courseBoardDTO.getPno()) {
    BoardPlacePK boardPlacePK=BoardPlacePK.builder()
        .day(courseBoardDTO.getDay())
        .orderNumber(courseBoardDTO.getOrderNumber())
        .board(board)
        .build();
      BoardPlace boardPlace = BoardPlace.builder()
          .boardPlacePK(boardPlacePK)
          .place(Place.builder().pno(pno).build())
          .build();
      boardPlaceRepository.save(boardPlace);
    }
    if (courseBoardDTO.getSrc()!=null) {
      for (String src : courseBoardDTO.getSrc()) {
        Image image = Image.builder()
            .src(src)
            .board(board)
            .build();
        imageRepository.save(image);
        log.info("image 저장");
      }
    }
    return board.getBno();
  }

  @Transactional
  @Override
  public Long remove(Long bno) {
    replyRepository.deleteByChildRno(bno);
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

      if (boardDTO.getSrc() !=null ) {
        //이미지를 조회
        List<Image> images = imageRepository.selectImageByBno(boardDTO.getBno());
        List<Image> images1 = new ArrayList<>(images);

        // 이미지가 없는 경우 이미지 추가
        for (String src : boardDTO.getSrc()) {
          boolean exists = images.stream().anyMatch(image -> image.getSrc().equals(src));
          if (!exists) {
            Image image = Image.builder()
                .board(Board.builder().bno(boardDTO.getBno()).build())
                .src(src)
                .build();
            images.add(image);
            images1.add(image);
          }
        }

        //제거해야하는 이미지
        images1.removeIf(image -> boardDTO.getSrc().contains(image.getSrc()));
        for (Image image : images1) {
          imageRepository.deleteById(image.getIno());
        }

        // 이미지 db에 값을 보드 dto와 비교해서 맞지않으면 삭제
        images.removeIf(image -> !boardDTO.getSrc().contains(image.getSrc()));

        //새로운 이미지 db 추가와 삭제된 db를 저장
        for (Image image : images) {
          imageRepository.save(image);
        }
      }
      return board.getBno();
    }
      return -1l;
    }

//  @Override
//  public BoardDTO getPlaceBoardByBno(Long bno) {
//    List<Object[]> result = boardRepository.getPlaceBoardByBno(bno);
//    if (result.isEmpty()){
//      return null;
//    } else {
//      Board board = (Board) result.get(0)[0];
//      return entityToDto(board);
//    }
//  }
//
//  @Override
//  public BoardDTO getCourseBoardByBno(Long bno) {
//    List<Object[]> result = boardRepository.getCourseBoardByBno(bno);
//    if (result.isEmpty()){
//      return null;
//    } else {
//      Board board = (Board) result.get(0)[0];
//      return entityToDto(board);
//    }
//  }

  //회원별 장소 포스팅 정보 조회
//  @Override
//  public List<BoardDTO> getBoardByMno(Long mno) {
//    List<Object[]> result = boardRepository.getBoardByMno(mno);
//    List<BoardDTO>boardDTOS=new ArrayList<>();
//    Map<Long, BoardDTO> boardMap = new HashMap<>();
//    if (result.isEmpty()){
//      return null;
//    } else {
//      for (Board board[]: result) {
//
//      }
//    }
//    return ;
//  }
}
