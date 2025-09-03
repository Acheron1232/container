package com.mykyda.kitchenserver.database.repository;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.entity.PizzaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {

    List<PizzaOrder> findAllByStatusOrderByCreatedAtAsc(PizzaStatus status);
}
