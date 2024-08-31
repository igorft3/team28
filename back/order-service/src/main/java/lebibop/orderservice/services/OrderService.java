package lebibop.orderservice.services;

import lebibop.orderservice.models.Order;
import lebibop.orderservice.kafkaProducer.MessageProducer;
import lebibop.orderservice.repositories.OrderRepository;
import lebibop.orderservice.DTO.SendToUserDTO;
import lebibop.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static lebibop.orderservice.DTO.SendToWarehouseDTO.fromOrder;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private MessageProducer messageProducer;

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatus(status);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findByOrderId(orderId).orElseThrow(() -> new NoSuchElementException("Заказ не найден"));
    }

    @Transactional
    public void updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        if (order.getOrderStatus().equals(OrderStatus.REJECTED.getDisplayName()))
            throw new IllegalArgumentException("Заказ отклонен, ему нельзя поменять статус");
        if (status.equals(OrderStatus.REJECTED.getDisplayName())) {
            messageProducer.sendToUser(new SendToUserDTO(order.getUserId(), order.getTotalPrice()));
            messageProducer.sendToWarehouse(fromOrder(order));
        }
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("Дата начала не может быть больше дата конца");
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }
}
