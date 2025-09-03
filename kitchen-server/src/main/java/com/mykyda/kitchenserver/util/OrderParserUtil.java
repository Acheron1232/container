package com.mykyda.kitchenserver.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykyda.kitchenserver.dto.OrderDTO;
import com.mykyda.kitchenserver.exception.OrderParserException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
@Slf4j
public class OrderParserUtil {

    private final ObjectMapper mapper = new ObjectMapper();

    public OrderDTO parse(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            OrderDTO order = new OrderDTO();
            order.setId(UUID.fromString(root.get("id").asText()));
            order.setFacilityId(UUID.fromString(root.get("facilityId").asText()));
            order.setStatus(OrderDTO.OrderStatus.valueOf(root.get("status").asText()));
            JsonNode createdAtNode = root.get("createdAt");
            LocalDateTime ldt = LocalDateTime.of(
                    createdAtNode.get(0).asInt(),
                    createdAtNode.get(1).asInt(),
                    createdAtNode.get(2).asInt(),
                    createdAtNode.get(3).asInt(),
                    createdAtNode.get(4).asInt(),
                    createdAtNode.get(5).asInt(),
                    createdAtNode.get(6).asInt()
            );
            order.setCreatedAt(Timestamp.valueOf(ldt));
            Map<UUID, Integer> items = new HashMap<>();
            for (JsonNode itemNode : root.get("items")) {
                UUID pizzaId = UUID.fromString(itemNode.get("pizzaId").asText());
                int quantity = itemNode.get("quantity").asInt();
                items.put(pizzaId, quantity);
            }
            order.setItems(items);
            return order;
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new OrderParserException(e.getMessage());
        }
    }
}
