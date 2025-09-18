package com.mykyda.deliveryserver.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykyda.deliveryserver.dto.ParsedDeliveryOrderDTO;
import com.mykyda.deliveryserver.dto.PizzaOrderDTO;
import com.mykyda.deliveryserver.exception.DeliveryOrderParserException;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PizzaOrderMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<PizzaOrderDTO> fromJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new DeliveryOrderParserException("Cannot parse pizzaOrders JSON" + e.getMessage());
        }
    }

    public static String toJson(List<ParsedDeliveryOrderDTO.ParsedPizzaOrder> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new DeliveryOrderParserException("Cannot serialize pizzaOrders to JSON" + e.getMessage());
        }
    }
}
