package lebibop.warehouseservice.service;

import lebibop.warehouseservice.DTO.CartItemDTO;
import lebibop.warehouseservice.model.Warehouse;
import lebibop.warehouseservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public List<String> checkAvailability(List<CartItemDTO> items) {
        List<String> unavailableItems = new ArrayList<>();

        for (CartItemDTO item : items) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findByProductId(item.getProductId());

            if (optionalWarehouse.isPresent()) {
                Warehouse warehouse = optionalWarehouse.get();
                if (warehouse.getQuantity() < item.getQuantity()) {
                    unavailableItems.add("Product ID " + item.getProductId() + " не доступен в нужном количестве. Запрошено: " + item.getQuantity() + "; Доступно: " + warehouse.getQuantity());
                }
            } else {
                unavailableItems.add("Product ID " + item.getProductId() + " не доступен.");
            }
        }

        return unavailableItems;
    }

    @Transactional
    public void updateStock(List<CartItemDTO> items) {
        for (CartItemDTO item : items) {
            Warehouse warehouse = warehouseRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Продукт с ID " + item.getProductId() + " не найден на складе."));

            int updatedQuantity = warehouse.getQuantity() - item.getQuantity();

            if (updatedQuantity < 0) {
                throw new IllegalArgumentException("Недостаточно товара на складе для продукта с ID " + item.getProductId());
            }

            warehouse.setQuantity(updatedQuantity);
            warehouseRepository.save(warehouse);
        }
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public List<Warehouse> findByStatus(String status) {
        return warehouseRepository.findByProductStatus(status);
    }

    public List<Warehouse> findWithQuantityGreaterThanZero() {
        return warehouseRepository.findByQuantityGreaterThan(0);
    }

    public List<Warehouse> findWithQuantityZero() {
        return warehouseRepository.findByQuantity(0);
    }

    @Transactional
    public Warehouse updateStatus(Long warehouseId, String newStatus) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setProductStatus(newStatus);
        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public Warehouse updateQuantity(Long warehouseId, Integer newQuantity) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setQuantity(newQuantity);
        return warehouseRepository.save(warehouse);
    }
}
