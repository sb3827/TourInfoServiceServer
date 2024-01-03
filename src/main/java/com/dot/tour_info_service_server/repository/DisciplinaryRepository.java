package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Disciplinary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DisciplinaryRepository extends JpaRepository<Disciplinary,Long> {
    //유저에 대한 모든 징계 조회
    List<Disciplinary> findAllByMemberMnoOrderByExpDateDesc(Long mno);

    @Modifying
    @Transactional
    @Query("update Disciplinary d set d.member.mno = null where d.member.mno = :mno")
    void setNullMno(Long mno);

}
