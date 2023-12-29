package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.BoardLike;
import com.yayum.tour_info_service_server.entity.BoardLikePK;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.BoardLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final BoardLikeRepository boardLikeRepository;

    @Override
    public boolean likeBoard(Long mno, Long bno) throws SQLException {
        BoardLikePK boardLikePK = BoardLikePK.builder()
                .board(Board.builder()
                        .bno(bno)
                        .build())
                .member(Member.builder()
                        .mno(mno)
                        .build())
                .build();

        BoardLike boardLike = BoardLike.builder()
                .boardLikePK(boardLikePK)
                .build();

        try {
            boardLikeRepository.save(boardLike);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SQLException("등록 불가");
        }

        return true;
    }

    @Override
    public boolean unLikeBoard(Long mno, Long bno) throws SQLException {
        BoardLikePK boardLikePK = BoardLikePK.builder()
                .board(Board.builder()
                        .bno(bno)
                        .build())
                .member(Member.builder()
                        .mno(mno)
                        .build())
                .build();

        try {
            boardLikeRepository.deleteById(boardLikePK);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SQLException("삭제 불가");
        }

        return true;
    }
}
