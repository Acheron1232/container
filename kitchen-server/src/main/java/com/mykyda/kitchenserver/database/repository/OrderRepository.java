package com.mykyda.kitchenserver.database.repository;

import com.mykyda.kitchenserver.database.entity.Order;
import com.mykyda.kitchenserver.database.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> getOrdersByStatusNotAndFacilityIdOrderByCreatedAtAsc(OrderStatus orderStatus, UUID facilityId);
}