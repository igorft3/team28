package lebibop.warehouseservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lebibop.warehouseservice.DTO.CartItemRequest;
import lebibop.warehouseservice.model.Warehouse;
import lebibop.warehouseservice.repository.WarehouseRepository;
import lebibop.warehouseservice.service.ExternalService;
import lebibop.warehouseservice.service.WarehouseService;
import lebibop.warehouseservice.swagger.WarehouseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lebibop.warehouseservice.DTO.CartItemDTO;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WarehouseController implements WarehouseAPI {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseService warehouseService;
    private final ExternalService externalService;

    @Autowired
    public WarehouseController(WarehouseRepository warehouseRepository, WarehouseService warehouseService, ExternalService externalService) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseService = warehouseService;
        this.externalService = externalService;
    }

    @Hidden
    @PostMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody CartItemRequest cartItemsRequest,
                                               HttpServletRequest request) {
        if (!externalService.getExternalToken().equals(request.getHeader("External-Token"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Отказ в доступе");
        }
        try {
            List<CartItemDTO> items = cartItemsRequest.getItems();
            List<String> unavailableItems = warehouseService.checkAvailability(items);
            if (unavailableItems.isEmpty()) {
                externalService.checkBalanceToExternalService(cartItemsRequest.getPrice(), request.getHeader("Authorization").substring(7));
                warehouseService.updateStock(items);
                return ResponseEntity.ok("Все товары успешно забронированы на складе");
            } else {
                return ResponseEntity.status(400).body(unavailableItems);
            }
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getAllWarehouses() {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.findAll());
    }

    public ResponseEntity<?> getWarehouseById(@PathVariable Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        return warehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> getWarehouseByProductId(@PathVariable Long productId) {
        Optional<Warehouse> warehouse = warehouseRepository.findByProductId(productId);
        return warehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> getWarehousesByStatus(@RequestParam String status) {
        return ResponseEntity.ok(warehouseService.findByStatus(status));
    }

    public ResponseEntity<?> getWarehousesWithQuantityGreaterThanZero() {
        return ResponseEntity.ok(warehouseService.findWithQuantityGreaterThanZero());
    }

    public ResponseEntity<?> getWarehousesWithQuantityZero() {
        return ResponseEntity.ok(warehouseService.findWithQuantityZero());
    }

    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Warehouse updatedWarehouse = warehouseService.updateStatus(id, status);
        return ResponseEntity.ok(updatedWarehouse);
    }

    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Warehouse updatedWarehouse = warehouseService.updateQuantity(id, quantity);
        return ResponseEntity.ok(updatedWarehouse);
    }
}