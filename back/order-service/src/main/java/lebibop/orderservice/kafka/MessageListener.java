package lebibop.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lebibop.orderservice.models.Order;
import lebibop.orderservice.models.OrderItem;
import lebibop.orderservice.repositories.OrderRepository;
import lebibop.orderservice.DTO.CartDTO;
import lebibop.orderservice.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class MessageListener {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageListener(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "cartTopic", groupId = "group1")
    public void listen(String message) {
        try {
            CartDTO cartDTO = objectMapper.readValue(message, CartDTO.class);

            Order order = new Order();
            order.setUserId(cartDTO.getUserId());

            order.setCreatedAt(LocalDateTime.now());
            order.setTotalItems(cartDTO.getTotalItems());
            order.setTotalPrice(cartDTO.getTotalPrice());

            System.out.println(cartDTO.getUserId());
            System.out.println(cartDTO.getTotalItems());

            order.setOrderStatus(OrderStatus.IN_PROGRESS.getDisplayName());
            order.setOrderItems(cartDTO.getCartItems().stream()
                    .map(cartItemDTO -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(cartItemDTO.getProductId());
                        orderItem.setQuantity(cartItemDTO.getQuantity());
                        orderItem.setPrice(cartItemDTO.getPrice());
                        orderItem.setOrder(order);
                        return orderItem;
                    })
                    .collect(Collectors.toList()));

            orderRepository.save(order);

            System.out.println("Order created for user: " + cartDTO.getUserId());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
