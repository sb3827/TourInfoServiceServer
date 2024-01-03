package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.dot.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    // 회원 프로필 조회 ( mno, 이름, 팔로잉수, 팔로워수, 찜목록수, 이미지)
    @Query(value = "select m.mno as mno, m.name, count(f1.followPk.follower.mno) as followings, count(f2.followPk.member.mno) as followers, " +
            "count(c.cartPK.member.mno) as cart, m.image " +
            "from Member m left outer join Follow f1 on m.mno = f1.followPk.member.mno  " +
            "left outer join Follow f2 on m.mno = f2.followPk.follower.mno " +
            "left outer join Cart c on m.mno = c.cartPK.member.mno "+
            "where m.name like %:name% " +
            "group by m.mno")
    List<Object[]> findProfileByName(@Param("name") String name);

    // 회원 검색 ( mno, 이미지, 이름 )
    @Query("select m.mno, m.image, m.name from Member m where m.name like %:name%")
    List<Object[]> searchUser(@Param("name") String name);

    // 회원가입대기 조회
    @Query("select m.mno, m.name, m.email, m.businessId from Member m where m.isApprove = false")
    List<Object[]> showJoinWaiting();

    // 회원가입 승인
    @Modifying
    @Transactional
    @Query("update Member m set m.isApprove = true where m.mno = :mno")
    void joinMember(Long mno);

    // 회원정보조회
    @Query("select m.mno, m.image, m.name, m.email, m.phone, m.birth, m.roleSet from Member m where m.mno = :mno ")
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Object[]> userInfo(Long mno);
}
