package com.mykyda.kitchenserver.database.repository;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, UUID> {}