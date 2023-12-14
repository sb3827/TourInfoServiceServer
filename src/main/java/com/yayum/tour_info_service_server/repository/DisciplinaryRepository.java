package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Disciplinary;
import com.yayum.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaryRepository extends JpaRepository<Disciplinary,Long> {
    //유저에 대한 모든 징계 조회
    List<Disciplinary> findAllByMemberMno(Long mno);

    //해당 유저
}
