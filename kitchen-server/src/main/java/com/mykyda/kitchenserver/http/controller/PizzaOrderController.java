package com.mykyda.kitchenserver.http.controller;

import com.mykyda.kitchenserver.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kitchen")
public class PizzaOrderController {

    private final KitchenService kitchenService;

    @PatchMapping("/change-ready-quantity")
    public ResponseEntity<Object> changeReadyQuantity(@RequestBody Map<UUID, Integer> pizzaOrdersReady) {
        kitchenService.setReadyQuantities(pizzaOrdersReady);
        return ResponseEntity.noContent().build();
    }
}
