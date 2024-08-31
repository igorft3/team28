package lebibop.warehouseservice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Контроллер работы с пользователями", description="/auth/ - общий доступ; /admin/ - для пользователей с ролью ADMIN")
public interface WarehouseAPI {

    @Operation(summary = "Получить все записи",
            description = "Возвращает список всех записей в складе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка складов",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/privileged/get-all")
    ResponseEntity<?> getAllWarehouses();

    @Operation(summary = "Получить запись по id",
            description = "Возвращает запись в складе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/privileged/get-by-id/{warehouseId}")
    ResponseEntity<?> getWarehouseById(
            @Parameter(description = "Id продукта", example = "1")
            @PathVariable Long warehouseId);

    @Operation(summary = "Получить запись по productId",
            description = "Возвращает запись в складе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/privileged/get-by-product/{productId}")
    ResponseEntity<?> getWarehouseByProductId(
            @Parameter(description = "Id продукта", example = "1")
            @PathVariable Long productId);


    @GetMapping("/privileged/status/{status}")
    ResponseEntity<?> getWarehousesByStatus(
            @Parameter(description = "Статус", example = "В ПРОДАЖЕ")
            @PathVariable String status);

    @Operation(summary = "Получить записи с количеством больше 0",
            description = "Возвращает записи в складе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/privileged/quantity/positive")
    ResponseEntity<?> getWarehousesWithQuantityGreaterThanZero();

    @Operation(summary = "Получить записи с количеством = 0",
            description = "Возвращает записи в складе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/privileged/quantity/zero")
    ResponseEntity<?> getWarehousesWithQuantityZero();


    @Operation(summary = "Изменение статуса", description = "Изменение статуса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Роль успешно изменена")))})
    @PutMapping("/privileged/update-status/{id}")
    ResponseEntity<?> updateStatus(
            @Parameter(description = "Id записи", example = "1")
            @PathVariable Long id, @RequestParam String status);

    @Operation(summary = "Изменение количества", description = "Изменение количества")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Количество товара успешно изменено")))})
    @PutMapping("/privileged/update-quantity/{id}")
    ResponseEntity<?> updateQuantity(
            @Parameter(description = "Id продукта", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Количество", example = "10")
            @RequestParam Integer quantity);
}
