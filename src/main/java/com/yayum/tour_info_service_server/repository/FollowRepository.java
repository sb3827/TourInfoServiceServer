package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

  List<Follow> getFollowerByMember(Member member);

}
