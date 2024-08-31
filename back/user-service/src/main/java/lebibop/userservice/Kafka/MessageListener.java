package lebibop.userservice.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lebibop.userservice.DTO.SendToUserDTO;
import lebibop.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageListener(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "cancelOrderToUser", groupId = "group1")
    public void listen(String message) {
        try {
            SendToUserDTO sendToUserDTO = objectMapper.readValue(message, SendToUserDTO.class);
            userService.addPrice(sendToUserDTO.getUserId(), sendToUserDTO.getPrice());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

    }
}
