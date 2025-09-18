package com.mykyda.deliveryserver.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedDeliveryOrderDTO {

    private UUID id;

    private UUID facilityId;

    private String status;

    private Long createdAt;

    private String type;

    private List<ParsedPizzaOrder> pizzaOrders;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParsedPizzaOrder {

        private UUID pizzaTemplateId;

        @JsonAlias("quantityReady")
        private Integer quantity;
    }
}
