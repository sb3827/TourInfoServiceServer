package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findById(@Param("mno") Long mno);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByEmail(@Param("email") String email);

    //@Query("select * from Member m where m.name =:name and m.phone =:phone")
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // 중복 여부(true: 중복, false: 중복x)
    boolean existsByEmail(String email);
}
