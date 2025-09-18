package com.mykyda.deliveryserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykyda.deliveryserver.dto.DeliveryOrderDTO;
import com.mykyda.deliveryserver.exception.DeliveryOrderParserException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeliveryOrderParser {

    public static DeliveryOrderDTO parse(String message) {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, DeliveryOrderDTO.class);
        } catch (Exception e) {
            throw new DeliveryOrderParserException(e.getMessage());
        }
    }
}
