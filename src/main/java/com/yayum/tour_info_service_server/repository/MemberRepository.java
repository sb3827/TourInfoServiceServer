package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.MemberDTO;
import com.yayum.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberByEmail(@Param("email") String email);
    Optional<Member> findByEmail(@Param("email") String email);

    //@Query("select * from Member m where m.name =:name and m.phone =:phone")
    Member findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // todo test
    boolean existsByEmail(String email);
}
