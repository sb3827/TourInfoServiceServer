package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardPlacePKDTO;
import com.yayum.tour_info_service_server.dto.CourseBoardDTO;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.BoardPlaceRepository;
import com.yayum.tour_info_service_server.repository.BoardRepository;
import com.yayum.tour_info_service_server.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardPlaceRepository boardPlaceRepository;
    private final ImageRepository imageRepository;

    // 코스 수정 service logic
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
}
