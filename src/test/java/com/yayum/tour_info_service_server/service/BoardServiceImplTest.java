package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardPlacePKDTO;
import com.yayum.tour_info_service_server.dto.CourseBoardDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class BoardServiceImplTest {
    @Autowired
    BoardService boardService;

    @Test
    public void modifyCourseTest() {
        try {
            List<BoardPlacePKDTO> coursePlaceList = new ArrayList<>();
            IntStream.rangeClosed(1, 4).forEach(i -> {
                coursePlaceList.add(BoardPlacePKDTO.builder()
                                .pno((long) i)
                                .day((i-1)/2)
                                .orderNumber(i%2)
                        .build());
            });

            List<String> srcList = new ArrayList<>();
            IntStream.rangeClosed(1,5).forEach(i -> {
                srcList.add("이미지" + (i+1) + " 경로");
            });

            log.info(boardService.modifyCourse(CourseBoardDTO.builder()
                            .bno(4L)
                            .title("modified title")
                            .content("modified content")
                            .isAd(false)
                            .isCourse(true)
                            .score(0.0)
                            .likes(0)
                            .writer(5L)
                            .coursePlaceList(coursePlaceList)
                            .srcList(srcList)
                    .build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}