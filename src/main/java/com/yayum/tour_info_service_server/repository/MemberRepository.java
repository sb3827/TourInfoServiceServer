package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.MemberDTO;
import com.yayum.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@Param("email") String email);

    //@Query("select * from Member m where m.name =:name and m.phone =:phone")
    Optional<Member> findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // 중복 여부(true: 중복, false: 중복x)
    boolean existsByEmail(String email);
}
