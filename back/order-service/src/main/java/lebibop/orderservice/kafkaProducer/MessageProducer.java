package lebibop.orderservice.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lebibop.orderservice.DTO.SendToUserDTO;
import lebibop.orderservice.DTO.SendToWarehouseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToUser(SendToUserDTO sendToUserDTO) {
        try {
            String cartJson = objectMapper.writeValueAsString(sendToUserDTO);
            kafkaTemplate.send("cancelOrderToUser", cartJson);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendToWarehouse(SendToWarehouseDTO sendToWarehouseDTO) {
        try {
            String cartJson = objectMapper.writeValueAsString(sendToWarehouseDTO);
            kafkaTemplate.send("cancelOrderToWarehouse", cartJson);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
