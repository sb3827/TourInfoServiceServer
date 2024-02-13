package com.dot.tour_info_service_server.service.place;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.repository.*;
import com.dot.tour_info_service_server.entity.Place;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final BoardPlaceRepository boardPlaceRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final CartRepository cartRepository;
    private final ImageRepository imageRepository;
    private final ReplyRepository replyRepository;
    private final ReportRepository reportRepository;

    // 장소 등록
    @Override
    public Long registerPlace(RegistPlaceRequestDTO placeDTO) {
        Place place = dtoToEntity(placeDTO);
        placeRepository.save(place);
        return place.getPno();
    }

    // 장소 검색
    @Override
    public List<PlaceDTO> searchPlace(Category filter, String search, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Object[]> pages = placeRepository.searchPlace(filter, search, pageRequest);
        List<Object[]> result = pages.getContent();
        List<PlaceDTO> placeList = new ArrayList<>();
        if (!result.isEmpty()) {
            for (Object[] list : result) {
                List<Object[]> placeImage = boardPlaceRepository.loadRepresentPlaceImageByPno((Long) list[0]);
                PlaceDTO placeDTO = PlaceDTO.builder()
                        .pno((Long) list[0])
                        .name((String) list[1])
                        .lng((Double) list[2])
                        .lat((Double) list[3])
                        .roadAddress((String) list[4])
                        .localAddress((String) list[5])
                        .engAddress((String) list[6])
                        .category((Category) list[7])
                        .cart((int) list[8])
                        .regDate((LocalDateTime) list[9])
                        .modDate((LocalDateTime) list[10])
                        .build();

                if (placeImage != null && !placeImage.isEmpty()) {
                    Object[] imageArray = placeImage.get(0);
                    if (imageArray != null && imageArray.length > 0) {
                        placeDTO.setImage((String) imageArray[0]);
                    } else {
                        placeDTO.setImage(null);
                    }
                } else {
                    placeDTO.setImage(null);
                }
                placeList.add(placeDTO);
            }
            return placeList;
        }
        return null;
    }

    // 방문한 장소 수
    @Override
    @Transactional
    public Map<String, Object> getPlaceCount(Long mno) {
        List<Object[]> result = placeRepository.getPlaceCount(mno);
        Map<String, Object> placeCount = new HashMap<>();
        if (!result.isEmpty()) {
            for (Object[] list : result) {
                for (int i = 0; i < list.length; i += 2) {
                    placeCount.put((String) list[i], list[i + 1]);
                }
            }
            return placeCount;
        }
        return null;
    }

    @Override
    public boolean modifyPlace(Long pno, String name) {
        Optional<Place> result = placeRepository.findById(pno);

        if(result.isEmpty()){
            return false;
        }

        Place place = result.get();
        place.changeName(name);

        placeRepository.save(place);
        return true;
    }

    // 장소 삭제
    @Override
    @Transactional
    public void removePlace(Long pno) {
        List<Board> result = boardRepository.getBoardsInfo(pno);

        if(!result.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                Board board = result.get(i);
                if (board.getIsCourse().equals(true)) { // course-place list 수정
                    boardPlaceRepository.updateBoardPlacePno(pno, board.getBno()); // boardPlace pno를 null로 변경
                }

                // board가 장소 or 코스+연결된 장소x
                if (board.getIsCourse().equals(false) || !boardPlaceRepository.existsBoardPlaceByBoard(board.getBno())) {
                    replyRepository.removeChildReply(pno); // 대댓글 먼저 삭제
                    replyRepository.removeReply(pno); // 댓글 삭제
                    imageRepository.removeImage(pno); // 이미지 삭제
                    boardLikeRepository.removeBoardLike(pno); // 좋아요 삭제
                    boardPlaceRepository.deleteByBno(board.getBno()); // board-place 연결 제거
                    boardRepository.deleteById(board.getBno()); // board 삭제
                }
            }
        }

        cartRepository.removeCart(pno); // cart 삭제
        placeRepository.deleteById(pno); // 장소 삭제
    }
}



