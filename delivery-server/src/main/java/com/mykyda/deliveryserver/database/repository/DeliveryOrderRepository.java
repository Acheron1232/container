package com.mykyda.deliveryserver.database.repository;

import com.mykyda.deliveryserver.database.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, UUID> {}
