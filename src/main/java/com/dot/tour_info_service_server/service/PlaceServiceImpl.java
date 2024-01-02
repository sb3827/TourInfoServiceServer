package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.PlaceDTO;
import com.dot.tour_info_service_server.entity.Category;
import com.dot.tour_info_service_server.repository.*;
import com.dot.tour_info_service_server.entity.Place;
import com.yayum.tour_info_service_server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // 이미 등록된 장소일 경우 장소 등록하지 못하도록 처리
    @Override
    public Long registerPlace(PlaceDTO placeDTO) {
        log.info("DTO-------------------");
        log.info(placeDTO);

        Place place = dtoToEntity(placeDTO);
        placeRepository.save(place);
        return place.getPno();
    }

    @Override
    public List<Long> findPlace(Category filter, String search) {
        List<Place> result = placeRepository.findPlace(filter, search);
        List<Long> pnoList = new ArrayList<>();

        for (Place place : result) {
            pnoList.add(entityToDto(place).getPno());
        }
        return pnoList;
    }


    @Override
    public void removePlace(Long pno) {

        List<Long> deleteBnos = boardRepository.returnBnos(pno);

        // 장소만 등록되어있고 게시글은 없을 경우 장소만 삭제
        if (boardRepository.boardIsCourse(pno) == null) {
            placeRepository.deleteById(pno);
        } else {
            // 코스 게시글일 경우
            if (boardRepository.boardIsCourse(pno) == true) {
                boardPlaceRepository.updateBoardPlacePno(pno); // boardPlace pno를 null로 변경
                cartRepository.removeCart(pno); // cart 삭제
                placeRepository.deleteById(pno); // place 삭제
            }
            // 장소 포스팅 게시글일 경우
            else {
                replyRepository.removeChildReply(pno); // 대댓글 먼저 삭제
                replyRepository.removeReply(pno); // 댓글 삭제
                imageRepository.removeImage(pno); // 이미지 삭제
                boardLikeRepository.removeBoardLike(pno); // 좋아요 삭제
                reportRepository.updateReportBnoNull(boardRepository.returnBnos(pno)); // 리포트 null로 변경
                cartRepository.removeCart(pno); // cart 삭제
                boardPlaceRepository.removeBoardPlaceByPno(pno); // boardPlace 삭제
                // 게시글 삭제
                for ( Long bnos : deleteBnos){
                boardRepository.deleteById(bnos);
                }
                placeRepository.deleteById(pno);// 장소 삭제

            }
        }
    }
}

