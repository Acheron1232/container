package com.mykyda.deliveryserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykyda.deliveryserver.dto.ParsedDeliveryOrderDTO;
import com.mykyda.deliveryserver.exception.DeliveryOrderParserException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeliveryOrderParser {

    public static ParsedDeliveryOrderDTO parse(String message) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, ParsedDeliveryOrderDTO.class);
        } catch (Exception e) {
            throw new DeliveryOrderParserException(e.getMessage());
        }
    }
}
