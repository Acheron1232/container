package com.mykyda.deliveryserver.http.controller;

import com.mykyda.deliveryserver.dto.DeliveryOrderDTO;
import com.mykyda.deliveryserver.service.DeliveryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    @GetMapping("/get-delivery-orders")
    public List<DeliveryOrderDTO> getOrders() {
        return deliveryOrderService.getDeliveryOrders();
    }
}
