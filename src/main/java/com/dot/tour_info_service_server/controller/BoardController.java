package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    // 장소 포스팅 등록
    @PostMapping(value = {"/place/posting/register"})
    public ResponseEntity<Map<String, Long>> placeRegisterPost(@RequestBody PlaceBoardDTO placeBoardDTO) {
        log.info("DTO: " + placeBoardDTO);
        Map<String, Long> result = new HashMap<>();
        Long bno = boardService.placeRegister(placeBoardDTO);
        log.info("bno: "+ bno);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스포스팅 등록
    @PostMapping(value = {"/course/posting/register"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Long>> courseRegisterPost(@RequestPart CourseBoardDTO courseBoardDTO,
                                                                @RequestPart MultipartFile[] imageFiles) {

        for (MultipartFile imageFile: imageFiles) {

            String originalName = imageFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            log.info("fileName: "+ fileName );
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator +
                    uuid + "_" + fileName;

            Path savePath = Paths.get(saveName); // Path : 파일 올리는 지정된 경로를 가리킬 때 사용

            try {
                imageFile.transferTo(savePath);
                Files.write(savePath, imageFile.getBytes());
                log.info("savePath: "+ savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, Long> result = new HashMap<>();
        Long bno = boardService.courseRegister(courseBoardDTO, imageFiles);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    // 장소,코스 포스팅 삭제
    @DeleteMapping(value = {"/place/posting/delete", "/course/posting/delete"})
    public ResponseEntity<Map<String, Long>> remove(@RequestParam("bno") Long bno) {
        Map<String, Long> result = new HashMap<>();
        boardService.remove(bno);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 장소 포스팅 수정
    @PutMapping(value = {"/place/posting/update"})
    public ResponseEntity<Map<String, Long>> modifyPlace(@RequestBody PlaceBoardDTO placeBoardDTO) {
        log.info("modify...dto: " + placeBoardDTO);
        Map<String, Long> result = new HashMap<>();
//        if (!SecurityUtil.validateMno(placeBoardDTO.getWriter())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        try {
            Long bno = boardService.placeBoardModify(placeBoardDTO);
            result.put("bno", bno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 코스 게시글 수정 address
    @PutMapping("/course/posting/update")
    public ResponseEntity<Map<String, Long>> modifyCourse(@RequestBody CourseBoardDTO courseBoardDTO) {

        log.info("courseBoardDTO: " + courseBoardDTO);
        Map<String, Long> result = new HashMap<>();
        // token 없이 controller Test시 제거할 것
//    if (!SecurityUtil.validateMno(courseBoardDTO.getWriter())) {
//      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
        try {
            Long bno = boardService.modifyCourse(courseBoardDTO);
            result.put("bno", bno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 장소, 코스 포스팅 정보 조회
    @GetMapping(value = {"/place/posting/bno", "/course/posting/bno"})
    public ResponseEntity<BoardDTO> getPlaceBoard(@RequestParam("bno") Long bno) {
        log.info("getPlaceBoard... bno: " + bno);
        BoardDTO boardDTO = boardService.getBoardByBno(bno);
        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    // 보드 메인페이지 정보 조회
    @GetMapping(value = "/main")
    public ResponseEntity<ResponseWrapDTO<MainResponseDTO>> boardMain(@RequestBody MnoDTO mnoDTO) {
        ResponseWrapDTO response = new ResponseWrapDTO(true, boardService.mainBoard(mnoDTO.getMno()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //   회원별 장소 포스팅 정보 조회
    @GetMapping(value = {"/place/posting/mno"})
    public ResponseEntity<List<BoardReplyCountDTO>> getBoardByMno(@RequestParam("mno") Long mno) {
        log.info("getBoardByMno... bno: " + mno);
        List<BoardReplyCountDTO> boardReplyCountDTO = boardService.getBoardByMno(mno);
        return new ResponseEntity<>(boardReplyCountDTO, HttpStatus.OK);
    }

    // 장소별 장소 포스팅 정보 조회
    @GetMapping(value = {"/place"})
    public ResponseEntity<List<BoardPlaceReplyCountDTO>> getBoardByPno(@RequestParam("pno") Long pno) {
        log.info("getBoardByMno... bno: " + pno);
        List<BoardPlaceReplyCountDTO> boardPlaceReplyCountDTO = boardService.getBoardByPno(pno);
        return new ResponseEntity<>(boardPlaceReplyCountDTO, HttpStatus.OK);
    }

    // 회원별 코스 포스팅 정보 조회
    @GetMapping(value = {"/course/posting/mno"})
    public ResponseEntity<List<BoardReplyCountDTO>> getCourseBoardByMno(@RequestParam("mno") Long mno) {
        log.info("getBoardByMno... bno: " + mno);
        List<BoardReplyCountDTO> boardReplyCountDTO = boardService.getCourseBoardByMno(mno);
        return new ResponseEntity<>(boardReplyCountDTO, HttpStatus.OK);
    }

    // 코스 검색 조회
    @GetMapping(value = {"/course"})
    public ResponseEntity<List<BoardSearchDTO>> findCourseBoard(@RequestParam("search") String search) {
        log.info("Search.... : "+search);
        List<BoardSearchDTO> boardSearchDTO = boardService.findCourseBoard(search);
        return new ResponseEntity<>(boardSearchDTO, HttpStatus.OK);
    }

}
