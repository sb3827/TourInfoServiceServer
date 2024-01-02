package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Disciplinary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaryRepository extends JpaRepository<Disciplinary,Long> {
    //유저에 대한 모든 징계 조회
    List<Disciplinary> findAllByMemberMnoOrderByExpDateDesc(Long mno);

}
