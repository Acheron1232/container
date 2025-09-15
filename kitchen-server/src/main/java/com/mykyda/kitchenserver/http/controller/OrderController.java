package com.mykyda.kitchenserver.http.controller;

import com.mykyda.kitchenserver.dto.OrderDTO;
import com.mykyda.kitchenserver.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/kitchen")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get-orders")
    public List<OrderDTO> getOrdersByFacilityId(@RequestParam("facilityId") UUID facilityId) {
        return orderService.getReceivedOrdersByFacilityId(facilityId);
    }
}
