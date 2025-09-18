package com.mykyda.deliveryserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryOrderDTO {

    private UUID id;

    private UUID facilityId;

    private String status;

    private Long createdAt;

    private String type;

    private List<PizzaOrderDTO> pizzaOrders;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PizzaOrderDTO {
        private UUID pizzaTemplateId;
        @JsonProperty("quantityReady")
        private Integer quantity;
    }
}
