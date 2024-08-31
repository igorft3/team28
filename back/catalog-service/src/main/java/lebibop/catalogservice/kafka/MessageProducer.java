package lebibop.catalogservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lebibop.catalogservice.models.Cart;
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

    public void sendCart(Cart cart) {
        try {
            String cartJson = objectMapper.writeValueAsString(cart);
            kafkaTemplate.send("cartTopic", cartJson);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendDeleteToWarehouse(Long id) {
        kafkaTemplate.send("DeleteProductTopic", String.valueOf(id));
    }

    public void sendAddToWarehouse(Long id) {
        kafkaTemplate.send("AddProductTopic", String.valueOf(id));
    }
}
