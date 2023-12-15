package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    
}
