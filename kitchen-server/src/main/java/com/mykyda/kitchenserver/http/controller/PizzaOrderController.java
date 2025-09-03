package com.mykyda.kitchenserver.http.controller;

import com.mykyda.kitchenserver.dto.PizzaOrderDTO;
import com.mykyda.kitchenserver.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchen")
public class PizzaOrderController {

    private final PizzaOrderService pizzaOrderService;

    @GetMapping("/get-ordered-pizza")
    private List<PizzaOrderDTO> getOrderedPizza() {
        return pizzaOrderService.getOrderedPizzas();
    }
}
