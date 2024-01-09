package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  private final ReplyRepository replyRepository;

  private final ImageRepository imageRepository;

  private final ReportRepository reportRepository;

  private final BoardPlaceRepository boardPlaceRepository;

  private final BoardLikeRepository boardLikeRepository;

  private final PlaceRepository placeRepository;

  @Override
  @Transactional
  public Long placeRegister(PlaceBoardDTO placeBoardDTO) {
    Board board = placeDtoToEntity(placeBoardDTO);
    boardRepository.save(board);
    log.info("board 저장");
    BoardPlacePK boardPlacePK = BoardPlacePK.builder()
        .day(1)
        .orderNumber(1)
        .board(board)
        .build();
    BoardPlace boardPlace = BoardPlace.builder()
        .boardPlacePK(boardPlacePK)
        .place(Place.builder().pno(placeBoardDTO.getPno()).build())
        .build();
    boardPlaceRepository.save(boardPlace);
    if (placeBoardDTO.getSrcList() != null) {
      for (String src : placeBoardDTO.getSrcList()) {
        Image image = Image.builder()
            .src(src)
            .board(board)
            .build();
        imageRepository.save(image);
        log.info("image 저장");
      }
    }
    imageRepository.deleteByNullBno();
    return board.getBno();
  }

  @Override
  @Transactional
  public Long courseRegister(CourseBoardDTO courseBoardDTO) {
    Board board = courseDtoToEntity(courseBoardDTO);
    board = boardRepository.save(board);
    log.info("board 저장" + board);

    for (BoardPlacePKDTO boardPlacePKDTO : courseBoardDTO.getCoursePlaceList()) {
      BoardPlacePK boardPlacePK = BoardPlacePK.builder()
          .day(boardPlacePKDTO.getDay())
          .orderNumber(boardPlacePKDTO.getOrderNumber())
          .board(Board.builder().bno(board.getBno()).build())
          .build();
      BoardPlace boardPlace = BoardPlace.builder()
          .boardPlacePK(boardPlacePK)
          .place(Place.builder().pno(boardPlacePKDTO.getPno()).build())
          .build();
      boardPlaceRepository.save(boardPlace);
    }

    if (courseBoardDTO.getSrcList() != null) {
      for (String src : courseBoardDTO.getSrcList()) {
        Image image = Image.builder()
            .src(src)
            .board(Board.builder().bno(board.getBno()).build())
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
  public Long placeBoardModify(PlaceBoardDTO placeBoardDTO) {
    Optional<Board> result = boardRepository.findById(placeBoardDTO.getBno());
    if (result.isEmpty()) {
      return -1L;
    }
    Board board = result.get();
    // dto to entity
    board.changeTitle(placeBoardDTO.getTitle());
    board.changeContent(placeBoardDTO.getContent());

    boardRepository.save(board);

    if (!(placeBoardDTO.getSrcList().isEmpty() || placeBoardDTO.getSrcList().equals(""))) {

      // db search
      List<Image> images = imageRepository.selectImageByBno(placeBoardDTO.getBno());
      Set<Image> imageSet = new HashSet<>(images);

      for (String img : placeBoardDTO.getSrcList()) {
        // setdb에서 DTO와 겹치는 것은 제거
        if (imageSet.contains(img)) {
          imageSet.remove(img);
        } else { // 겹치지 않은 부분은 추가
          Image image = Image.builder()
              .board(Board.builder().bno(placeBoardDTO.getBno()).build())
              .src(img)
              .build();
          imageRepository.save(image);
        }
      }
      // set에 저장된 데이터를 삭제 처리
      for (Image img : imageSet) {
        imageRepository.deleteById(img.getIno());
      }
    }
    return board.getBno();
  }

  @Transactional
  @Override
  public Long modifyCourse(CourseBoardDTO courseBoardDTO) throws IllegalAccessException, SQLException {
    Optional<Board> result = boardRepository.findBoardByBno(courseBoardDTO.getBno());

    if (result.isEmpty()) {
      throw new IllegalArgumentException("없는 게시글입니다.");
    }

    Board board = result.get();
    if (!board.getIsCourse()) {
      throw new IllegalAccessException("코스 게시글이 아닙니다.");
    }

    // 수정된 board update
    Board nBoard = Board.builder()
        .bno(courseBoardDTO.getBno())
        .title(courseBoardDTO.getTitle())
        .content(courseBoardDTO.getContent())
        .isAd(courseBoardDTO.getIsAd())
        .isCourse(board.getIsCourse())
        .score(courseBoardDTO.getScore())
        .likes(board.getLikes())
        .writer(Member.builder().mno(board.getWriter().getMno()).build())
        .build();
    try {
      boardRepository.save(nBoard);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SQLException("board 갱신 실패");
    }

    try {
      //BoardPlacePK 삭제 후 다시 생성 => update
      boardPlaceRepository.deleteAllByBoardPlacePKBoard(Board.builder().bno(board.getBno()).build());

      for (BoardPlacePKDTO coursePlace : courseBoardDTO.getCoursePlaceList()) {
        BoardPlace boardPlace = BoardPlace.builder()
            .boardPlacePK(BoardPlacePK.builder()
                .board(Board.builder()
                    .bno(board.getBno())
                    .build())
                .day(coursePlace.getDay())
                .orderNumber(coursePlace.getOrderNumber())
                .build())
            .place(Place.builder()
                .pno(coursePlace.getPno())
                .build())
            .build();

        boardPlaceRepository.save(boardPlace);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SQLException("BoardPlace 갱신 실패");
    }

    try {
      // 기존에 저장된 이미지와 변경된 이미지 변경 후 삭제 및 등록
      List<Image> imageList = imageRepository.findAllByBoard(Board.builder().bno(board.getBno()).build());
      Set<String> images = new HashSet<>(courseBoardDTO.getSrcList());

      for (Image img : imageList) {
        if (images.contains(img.getSrc())) {
          // dto의 src가 저장된 src일 경우
          images.remove(img.getSrc());
        } else {
          // dto에 없고, DB에 있는 경우
          // todo 실제 저장소에서 src로 삭제하는 logic 추가
          imageRepository.deleteById(img.getIno());
        }
      }

      for (String img : images) {
        Image image = Image.builder()
            .board(Board.builder()
                .bno(board.getBno())
                .build())
            .src(img)
            .build();

        // todo src에 해당하는 주소에 실제 저장하는 logic 추가
        imageRepository.save(image);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SQLException("image 갱신 실패");
    }

    return board.getBno();
  }

  @Override
  @Transactional
  public BoardInfoDTO getBoardByBno(Long bno) throws IllegalAccessException, SQLException {
    List<Object[]> result = boardRepository.getPlaceBoardByBno(bno);
      if (result.isEmpty()) {
        throw new IllegalArgumentException("없는 게시글입니다.");
      }
      List<Object[]> result2 = placeRepository.getPlaceByBoard(bno);
      List<Object[]> result3 = imageRepository.getImageByBno(bno);

      List<PlaceDTO> placeDTOS = new ArrayList<>();

      for (Object[] objects: result2) {
        PlaceDTO placeDTO = PlaceDTO.builder()
                .pno((Long) objects[0])
                .name((String) objects[1])
                .category((Category) objects[2])
                .lng((Double) objects[3])
                .lat((Double) objects[4])
                .roadAddress((String) objects[5])
                .localAddress((String) objects[6])
                .engAddress((String) objects[7])
                .cart((Integer) objects[8])
                .regDate((LocalDateTime) objects[9])
                .modDate((LocalDateTime) objects[10])
                .build();
        placeDTOS.add(placeDTO);
      }

      List<String> imageUrls = new ArrayList<>();
      for (Object[] image : result3) {
        String src = (String) image[0];
        imageUrls.add(src);
      }
      BoardInfoDTO nboardInfoDTO = BoardInfoDTO.builder()
              .title((String) result.get(0)[0])
              .content((String) result.get(0)[1])
              .mno((Long) result.get(0)[2])
              .writer((String)result.get(0)[3])
              .isCourse((Boolean) result.get(0)[4])
              .regdate((LocalDateTime) result.get(0)[5])
              .isAd((Boolean) result.get(0)[6])
              .likes((Integer) result.get(0)[7])
              .score((Double) result.get(0)[8])
              .mnoCnt((Long) result.get(0)[9])
              .placeDTOS(placeDTOS)
              .images(imageUrls.toArray(new String[0]))
              .build();
    return nboardInfoDTO;
  }

  @Override
  public BoardInfoDTO getCourseByBno (Long bno) throws IllegalAccessException, SQLException {
    List<Object[]> result = boardRepository.getCourseBoardByBno(bno);
    if (result.isEmpty()) {
      throw new IllegalArgumentException("없는 게시글입니다.");
    }
    List<Object[]> result2 = placeRepository.getPlaceByBoard(bno);
    List<Object[]> result3 = imageRepository.getImageByBno(bno);

    List<PlaceDTO> placeDTOS = new ArrayList<>();

    for (Object[] objects: result2) {
      PlaceDTO placeDTO = PlaceDTO.builder()
              .pno((Long) objects[0])
              .name((String) objects[1])
              .category((Category) objects[2])
              .lng((Double) objects[3])
              .lat((Double) objects[4])
              .roadAddress((String) objects[5])
              .localAddress((String) objects[6])
              .engAddress((String) objects[7])
              .cart((Integer) objects[8])
              .regDate((LocalDateTime) objects[9])
              .modDate((LocalDateTime) objects[10])
              .build();
      placeDTOS.add(placeDTO);
    }

    List<String> imageUrls = new ArrayList<>();
    for (Object[] image : result3) {
      String src = (String) image[0];
      imageUrls.add(src);
    }
    BoardInfoDTO nboardInfoDTO = BoardInfoDTO.builder()
            .title((String) result.get(0)[0])
            .content((String) result.get(0)[1])
            .mno((Long) result.get(0)[2])
            .writer((String)result.get(0)[3])
            .isCourse((Boolean) result.get(0)[4])
            .regdate((LocalDateTime) result.get(0)[5])
            .isAd((Boolean) result.get(0)[6])
            .likes((Integer) result.get(0)[7])
            .score((Double) result.get(0)[8])
            .mnoCnt((Long) result.get(0)[9])
            .placeDTOS(placeDTOS)
            .images(imageUrls.toArray(new String[0]))
            .build();
    return nboardInfoDTO;
  }


// 회원별 장소 포스팅 정보 조회
  @Override
  public List<BoardReplyCountDTO> getBoardByMno(Long mno) {
    List<Object[]> result = boardRepository.getBoardByMno(mno);
    List<BoardReplyCountDTO> boardReplyCountDTOS = new ArrayList<>();
    if (result.isEmpty()) {
      return null;
    }
    for (Object[] object: result){
      BoardReplyCountDTO boardReplyCountDTO = BoardReplyCountDTO.builder()
              .bno((Long)object[0])
              .title((String) object[1])
              .replyCount((Long)object[2])
              .regdate((LocalDateTime) object[3])
              .src((String) object[4])
              .likes((Integer)object[5])
              .score((Double) object[6])
              .writer((String) object[7])
              .build();
      boardReplyCountDTOS.add(boardReplyCountDTO);
    }
    return boardReplyCountDTOS;
  }

  //장소별 장소 포스팅 조회
  @Override
  public List<BoardPlaceReplyCountDTO> getBoardByPno(Long pno) {
    List<Object[]> result = boardRepository.getBoardByPno(pno);
    List<BoardPlaceReplyCountDTO> boardPlaceReplyCountDTOS = new ArrayList<>();

    if (result.isEmpty()) {
      return null;
    }

    for (Object[] objects : result) {
      // bno을 보내주면 image 배열을 받아오는 작업
      Long bno = (Long) objects[1];
      List<Object[]> images = imageRepository.getImageByBno(bno);
      List<String> imageUrls = new ArrayList<>();
      for (Object[] image : images) {
        String src = (String) image[0];
        imageUrls.add(src);
      }
      BoardPlaceReplyCountDTO boardPlaceReplyCountDTO = BoardPlaceReplyCountDTO.builder()
              .pno((Long)objects[0])
              .bno((Long) objects[1])
              .src(imageUrls.toArray(new String[0]))
              .title((String) objects[2])
              .replyCount((Long) objects[3])
              .writer((String) objects[4])
              .regdate((LocalDateTime) objects[5])
              .likes((Integer) objects[6])
              .score((Double) objects[7])
              .isAd((Boolean) objects[8])
              .lat((Double) objects[9])
              .lng((Double) objects[10])
              .engAddress((String) objects[11])
              .localAddress((String) objects[12])
              .roadAddress((String) objects[13])
              .name((String) objects[14])
              .build();
      boardPlaceReplyCountDTOS.add(boardPlaceReplyCountDTO);

    }
    return boardPlaceReplyCountDTOS;
  }

  @Override
  public List<BoardReplyCountDTO> getCourseBoardByMno(Long mno) {
    List<Object[]> result = boardRepository.getCourseBoardByMno(mno);
    List<BoardReplyCountDTO> boardReplyCountDTOS = new ArrayList<>();
    if (result.isEmpty()) {
      return null;
    }
    for (Object[] object: result){
      BoardReplyCountDTO boardReplyCountDTO = BoardReplyCountDTO.builder()
              .bno((Long)object[0])
              .title((String) object[1])
              .replyCount((Long)object[2])
              .regdate((LocalDateTime) object[3])
              .src((String) object[4])
              .likes((Integer)object[5])
              .score((Double) object[6])
              .writer((String) object[7])
              .build();
      boardReplyCountDTOS.add(boardReplyCountDTO);
    }
    return boardReplyCountDTOS;
  }

  @Override
  public List<BoardSearchDTO> findCourseBoard(String search) {
    List<Object[]> result = boardRepository.findCourseBoard(search);
    List<BoardSearchDTO> boardSearchDTOS = new ArrayList<>();
    if (result.isEmpty()) {
      return null;
    }
    for (Object[] objects: result) {
      // bno을 보내주면 image 배열을 받아오는 작업
      Long bno = (Long) objects[0];
      List<Object[]> images = imageRepository.getImageByBno(bno);
      List<String> imageUrls = new ArrayList<>();
      for (Object[] image : images) {
        String src = (String) image[0];
        imageUrls.add(src);
      }

      BoardSearchDTO boardSearchDTO = BoardSearchDTO.builder()
              .bno((Long) objects[0])
              .title((String) objects[1])
              .likes((Integer) objects[2])
              .score((Double) objects[3])
              .writer((String) objects[4])
              .regDate((LocalDateTime) objects[5])
              .isAd((Boolean) objects[6])
              .srcList(imageUrls.toArray(new String[0]))
              .build();
      boardSearchDTOS.add(boardSearchDTO);
    }
    return boardSearchDTOS;
  }

  // 메인 조회
  @Override
  public MainResponseDTO mainBoard(Long mno) {
    List<Object[]> result =placeRepository.mostLikePlace();
    List<MainPlaceResponseDTO> mainPlaceResponseDTOS=new ArrayList<>();
    for(Object[] objects:result){
      MainPlaceResponseDTO mainPlaceResponseDTO=MainPlaceResponseDTO.builder()
          .pno((Long)objects[0])
          .name((String)objects[1])
          .src((String)objects[2])
          .build();
      mainPlaceResponseDTOS.add(mainPlaceResponseDTO);
    }

    List<Object[]> result1=boardRepository.recentlyBoard();
    List<MainBoardResponseDTO> mainBoardResponseDTOS=new ArrayList<>();
    for(Object[] objects:result1){
      MainBoardResponseDTO mainBoardResponseDTO=MainBoardResponseDTO.builder()
          .bno((Long)objects[0])
          .title((String)objects[1])
          .src((String)objects[2])
          .build();
      mainBoardResponseDTOS.add(mainBoardResponseDTO);
    }

    List<Object[]> result2=boardRepository.mostLikeCourse();
    List<MainBoardResponseDTO> mainBoardResponseDTOS1=new ArrayList<>();
    for(Object[] objects:result2){
      MainBoardResponseDTO mainBoardResponseDTO=MainBoardResponseDTO.builder()
          .bno((Long)objects[0])
          .title((String)objects[1])
          .src((String)objects[2])
          .build();
      mainBoardResponseDTOS1.add(mainBoardResponseDTO);
    }

    List<Object[]> result3=boardRepository.followerBoard(mno);
    List<MainBoardResponseDTO> mainBoardResponseDTOS2=new ArrayList<>();
    for(Object[] objects:result3){
      MainBoardResponseDTO mainBoardResponseDTO=MainBoardResponseDTO.builder()
          .bno((Long)objects[0])
          .title((String)objects[1])
          .src((String)objects[2])
          .build();
      mainBoardResponseDTOS2.add(mainBoardResponseDTO);
    }

    List<Object[]> result4=boardRepository.adBoard();
    List<MainBoardResponseDTO> mainBoardResponseDTOS3=new ArrayList<>();
    for(Object[] objects:result4){
      MainBoardResponseDTO mainBoardResponseDTO=MainBoardResponseDTO.builder()
          .bno((Long)objects[0])
          .title((String)objects[1])
          .src((String)objects[2])
          .build();
      mainBoardResponseDTOS3.add(mainBoardResponseDTO);
    }

    MainResponseDTO mainResponseDTO=new MainResponseDTO(mainPlaceResponseDTOS,mainBoardResponseDTOS,mainBoardResponseDTOS1,mainBoardResponseDTOS2,mainBoardResponseDTOS3);

    System.out.println(mainResponseDTO);

    return mainResponseDTO;
  }

}
