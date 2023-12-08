package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.CartDTO;
import com.yayum.tour_info_service_server.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, CartDTO> {
}
