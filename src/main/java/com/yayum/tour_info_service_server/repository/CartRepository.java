package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Cart;
import com.yayum.tour_info_service_server.entity.CartPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, CartPK> {

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.cartPK.place.pno = :pno")
    void removeCart(Long pno);
}
