package com.dot.tour_info_service_server.service.report;

import com.dot.tour_info_service_server.dto.request.report.DisciplinaryRequestDTO;
import com.dot.tour_info_service_server.dto.request.report.ReportRequestDTO;
import com.dot.tour_info_service_server.dto.response.report.DisciplinaryAllResponseDTO;
import com.dot.tour_info_service_server.dto.response.report.ReportAllResponseDTO;
import com.dot.tour_info_service_server.dto.response.report.ReportResponseDTO;
import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final DisciplinaryRepository disciplinaryRepository;
    //게시글 및 댓글 삭제
    private final ReplyRepository replyRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardPlaceRepository boardPlaceRepository;
    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;

    //신고 필터 조회
    @Override
    public List<ReportResponseDTO> reportFilter(int page, String filter, String search) {
        //페이지 네이션
        PageRequest pageRequest = PageRequest.of(page, 10);


        List<ReportResponseDTO> reportResponseDTOS = new ArrayList<>();
        List<Report> result;

        //전체 조회
        switch (filter) {
            case "all" -> {
                Page<Report> entityPage = reportRepository.searchReportAll(search, pageRequest);
                result = entityPage.getContent();
            }

            //처리 중
            case "reporting" -> {
                Page<Report> entityPage = reportRepository.searchIsDone(false, search, pageRequest);
                result = entityPage.getContent();
            }

            //처리 완료
            case "reported" -> {
                Page<Report> entityPage = reportRepository.searchIsDone(true, search, pageRequest);
                result = entityPage.getContent();
            }

            //장소 처리중
            case "place_reporting" -> {
                Page<Report> entityPage = reportRepository.searchPlaceReport(false, search, pageRequest);
                result = entityPage.getContent();
            }

            //장소 처리완료
            case "place_reported" -> {
                Page<Report> entityPage = reportRepository.searchPlaceReport(true, search, pageRequest);
                result = entityPage.getContent();
            }

            //게시글 처리중
            case "board_reporting" -> {
                Page<Report> entityPage = reportRepository.searchBoardReport(false, search, pageRequest);
                result = entityPage.getContent();
            }

            //게시글 처리완료
            case "board_reported" -> {
                Page<Report> entityPage = reportRepository.searchBoardReport(true, search, pageRequest);
                result = entityPage.getContent();
            }

            //리뷰 처리중
            case "reply_reporting" -> {
                Page<Report> entityPage = reportRepository.searchReplyReport(false, search, pageRequest);
                result = entityPage.getContent();
            }

            //리뷰 처리완료
            case "reply_reported" -> {
                Page<Report> entityPage = reportRepository.searchReplyReport(true, search, pageRequest);
                result = entityPage.getContent();
            }
            default -> {
                return null;
            }
        }
        //reportDTO로 형변환

        for (Report report : result) {
            Optional<Member> com = memberRepository.findById(report.getComplainant_mno());
            Optional<Member> def = memberRepository.findById(report.getDefendant_mno());

            ReportResponseDTO reportResponseDTO = ReportResponseDTO.builder()
                    .sno(report.getSno())
                    .complainant_mno(report.getComplainant_mno())
                    .complainant(com.map(Member::getName).orElse(null))
                    .defendant_mno(report.getDefendant_mno())
                    .defendant(def.map(Member::getName).orElse(null))
                    .bno(report.getBoard_bno() != null ? report.getBoard_bno() : null)
                    .rno(report.getReply_rno() != null ? report.getReply_rno() : null)
                    .content(report.getContent())
                    .isDone(report.getIsDone())
                    .message(report.getMessage())
                    .regDate(report.getRegDate())
                    .build();
            reportResponseDTOS.add(reportResponseDTO);
        }
        return reportResponseDTOS;
    }

    //신고 정보 조회
    @Override
    public ReportAllResponseDTO reportDetail(Long sno) {
        Optional<Report> result = reportRepository.findById(sno);
        if (result.isPresent()) {
            Report report = result.get();
            return entityToDto(report);
        }
        return null;
    }

    //유저에대한 정지 정보
    @Override
    public List<DisciplinaryAllResponseDTO> disciplinaryUserData(Long mno) {
        //회원이 없을 경우 null 전달
        Optional<Member> member = memberRepository.findById(mno);
        if (member.isEmpty()) {
            return null;
        }

        List<Disciplinary> result = disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(mno);
        List<DisciplinaryAllResponseDTO> disciplinaryAllResponseDTOS = new ArrayList<>();
        for (Disciplinary disciplinary : result) {
            DisciplinaryAllResponseDTO disciplinaryAllResponseDTO = DisciplinaryAllResponseDTO.builder()
                    .dno(disciplinary.getDno())
                    .mno(disciplinary.getMember().getMno())
                    .reason(disciplinary.getReason())
                    .strDate(disciplinary.getStrDate())
                    .expDate(disciplinary.getExpDate())
                    .build();
            disciplinaryAllResponseDTOS.add(disciplinaryAllResponseDTO);
        }
        return disciplinaryAllResponseDTOS;
    }

    //신고
    @Override
    public Long report(ReportRequestDTO reportRequestDTO) {

        if (reportRepository.checkPlaceReport(reportRequestDTO.getPno(), reportRequestDTO.getComplainant()) != null) {
            return -1L;
        }
        if (reportRepository.checkBoardReport(reportRequestDTO.getBno(), reportRequestDTO.getComplainant()) != null) {
            return -1L;
        }
        if (reportRepository.checkReplyReport(reportRequestDTO.getRno(), reportRequestDTO.getComplainant()) != null) {
            return -1L;
        }
        //신고가 여러개로 들어올때
        if ((reportRequestDTO.getPno() != null ? 1 : 0) + (reportRequestDTO.getRno() != null ? 1 : 0) + (reportRequestDTO.getBno() != null ? 1 : 0) > 1) {
            return -1L;
        }
        //장소 게시글인데 신고당하는 유저가 있는경우
        if (reportRequestDTO.getPno() != null && reportRequestDTO.getDefendant() != null) {
            return -1L;
        }

        //신고하고자하는 회원이 없을 경우 null 전달
        if (reportRequestDTO.getDefendant() != null) {
            Optional<Member> member = memberRepository.findById(reportRequestDTO.getDefendant());

            if (member.isEmpty()) {
                return null;
            }
            if (reportRequestDTO.getRno() == null && reportRepository.checkBoardReport(reportRequestDTO.getBno(), reportRequestDTO.getComplainant()) != null) {
                return -1L;
            }
            if (reportRequestDTO.getBno() == null && reportRepository.checkReplyReport(reportRequestDTO.getRno(), reportRequestDTO.getComplainant()) != null) {

                return -1L;
            }
        }

        Report report = Report.builder()
                .complainant_mno(reportRequestDTO.getComplainant())
                .defendant_mno(reportRequestDTO.getDefendant())
                .board_bno(reportRequestDTO.getBno() != null ? reportRequestDTO.getBno() : null)
                .reply_rno(reportRequestDTO.getRno() != null ? reportRequestDTO.getRno() : null)
                .content(reportRequestDTO.getContent())
                .message(reportRequestDTO.getMessage())
                .isDone(false)
                .build();
        reportRepository.save(report);
        return 1L;
    }

    //신고 상태 업데이트
    @Override
    public Long reportUpdate(Long sno) {
        Optional<Report> result = reportRepository.findById(sno);
        if (result.isPresent()) {
            Report report = result.get();
            report.changeIsDone(true);
            reportRepository.save(report);
            return report.getSno();
        }
        return -1L;
    }

    //제재 - merge후 board,reply delete 추가해야함
    @Override
    @Transactional
    public Long disciplinary(DisciplinaryRequestDTO disciplinaryRequestDTO) {
        List<Disciplinary> checkDisciplinary = disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(disciplinaryRequestDTO.getMno());

        //이미 정지된 유저
        if (!checkDisciplinary.isEmpty() && !checkDisciplinary.get(0).getExpDate().isBefore(LocalDateTime.now())) {
            return -1L;
        }

        int row = checkDisciplinary.size();

        Disciplinary disciplinary;
        if (row >= 4) {
            disciplinary = Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(null)
                    .build();
        } else if (row == 3) {
            disciplinary = Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(30))
                    .build();
        } else if (row == 2) {
            disciplinary = Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(14))
                    .build();

        } else if (row == 1) {
            disciplinary = Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(7))
                    .build();

        } else {
            disciplinary = Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(3))
                    .build();
        }

        //해당 게시글 및 댓글 삭제 -> merge 후 겹치는부분 제거
        Optional<Report> report = reportRepository.findById(disciplinaryRequestDTO.getSno());

        if (report.isPresent()) {
            Report result = report.get();

            if (result.getBoard_bno() != null) { // 게시글 삭제

                //댓글 삭제
                replyRepository.deleteAllByBoard(Board.builder().bno(result.getBoard_bno()).build());

                //좋아요 삭제
                boardLikeRepository.deleteAllByBoardLikePKBoard(Board.builder().bno(result.getBoard_bno()).build());

                //이미지 삭제
                imageRepository.deleteAllByBoard(Board.builder().bno(result.getBoard_bno()).build());

                //BoardPlace 삭제
                boardPlaceRepository.deleteAllByBoardPlacePKBoard(Board.builder().bno(result.getBoard_bno()).build());

                //Board 삭제
                boardRepository.deleteById(result.getBoard_bno());

            } else if (result.getReply_rno() != null) { //댓글 처리
                //대댓글이 없는 경우
                if (replyRepository.countAllByParent(Reply.builder().rno(result.getReply_rno()).build()) == 0) {
                    replyRepository.deleteById(result.getReply_rno());
                }
                //대댓글이거나 대댓글이 존재하는 경우
                else {
                    Optional<Reply> checkReply = replyRepository.findById(result.getReply_rno());
                    if (checkReply.isPresent()) {
                        Reply r = checkReply.get();

                        Reply reply = Reply.builder()
                                .rno(result.getReply_rno())
                                .parent(r.getParent() != null ? r.getParent() : null)
                                .member(null)
                                .board(Board.builder().bno(r.getBoard().getBno()).build())
                                .text("신고 처리된 댓글 입니다.")
                                .build();
                        replyRepository.save(reply);
                    }
                }
            }
            //게시글, 댓글 둘다 있는경우 오류 처리
            else {
                return -2L;
            }
        } else {
            //신고가 존재하지 않는 경우
            return -3L;
        }
        //신고 상태 업데이트 -> 신고 처리 완료
        reportUpdate(disciplinaryRequestDTO.getSno());

        //제재
        disciplinaryRepository.save(disciplinary);
        return disciplinary.getDno();
    }
}
