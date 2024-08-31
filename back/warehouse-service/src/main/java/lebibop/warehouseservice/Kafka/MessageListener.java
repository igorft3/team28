package lebibop.warehouseservice.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lebibop.warehouseservice.DTO.SendToWarehouseDTO;
import lebibop.warehouseservice.model.Warehouse;
import lebibop.warehouseservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MessageListener {

    private final WarehouseRepository warehouseRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageListener(WarehouseRepository warehouseRepository, ObjectMapper objectMapper) {
        this.warehouseRepository = warehouseRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "DeleteProductTopic", groupId = "group2")
    public void listenForDelete(String message) {
        Long id = Long.parseLong(message);
        Optional<Warehouse> warehouse = warehouseRepository.findByProductId(id);
        warehouse.ifPresent(warehouseRepository::delete);
    }

    @KafkaListener(topics = "AddProductTopic", groupId = "group2")
    public void listenForAdd(String message) {
        Long id = Long.parseLong(message);
        Warehouse warehouse = new Warehouse();
        warehouse.setProductId(id);
        warehouse.setQuantity(0);
        warehouse.setProductStatus("СНЯТ С ПРОДАЖИ");
        warehouseRepository.save(warehouse);
    }

    @KafkaListener(topics = "cancelOrderToWarehouse", groupId = "group2")
    public void listenForD(String message) {
        try {
            SendToWarehouseDTO sendToWarehouseDTO = objectMapper.readValue(message, SendToWarehouseDTO.class);
            for (SendToWarehouseDTO.ProductQuantityDTO item : sendToWarehouseDTO.getItems()) {
                Optional<Warehouse> warehouseItem = warehouseRepository.findByProductId(item.getProductId());

                if (warehouseItem.isPresent()) {
                    Warehouse w = warehouseItem.get();
                    w.setQuantity(w.getQuantity() + item.getQuantity());
                    warehouseRepository.save(w);
                }
            }

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
