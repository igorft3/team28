package lebibop.orderservice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name="Контроллер работы с заказами", description="/")
public interface OrderAPI {
    @Operation(summary = "Получения списка всех заказов", description = "Получения списка всех заказов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      },
                                      {
                                        "orderId": 2,
                                        "userId": 1,
                                        "orderStatus": "В РАБОТЕ",
                                        "totalItems": 20,
                                        "totalPrice": 7999.8,
                                        "createdAt": "2024-08-26T11:22:58.592399",
                                        "updatedAt": "2024-08-26T11:22:58.592682",
                                        "orderItems": [
                                          {
                                            "orderItemId": 2,
                                            "productId": 2,
                                            "quantity": 20,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 0
                                      }""")))})
    @GetMapping("/privileged/get-all")
    ResponseEntity<?> getAllOrders();


    @Operation(summary = "Список заказов конкретного пользователя", description = "Получение списка заказов конкретного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      },
                                      {
                                        "orderId": 2,
                                        "userId": 1,
                                        "orderStatus": "В РАБОТЕ",
                                        "totalItems": 20,
                                        "totalPrice": 7999.8,
                                        "createdAt": "2024-08-26T11:22:58.592399",
                                        "updatedAt": "2024-08-26T11:22:58.592682",
                                        "orderItems": [
                                          {
                                            "orderItemId": 2,
                                            "productId": 2,
                                            "quantity": 20,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 0
                                      }""")))})
    @GetMapping("/privileged/get-by-user/{userId}")
    ResponseEntity<?> getOrdersByUserId(
            @Parameter(description = "ID пользователя", example = "4")
            @PathVariable Long userId);

    @Operation(summary = "Список заказов текущего пользователя", description = "Получение списка заказов текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      },
                                      {
                                        "orderId": 2,
                                        "userId": 1,
                                        "orderStatus": "В РАБОТЕ",
                                        "totalItems": 20,
                                        "totalPrice": 7999.8,
                                        "createdAt": "2024-08-26T11:22:58.592399",
                                        "updatedAt": "2024-08-26T11:22:58.592682",
                                        "orderItems": [
                                          {
                                            "orderItemId": 2,
                                            "productId": 2,
                                            "quantity": 20,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 0
                                      }""")))})
    @GetMapping("/get-by-current-user")
    ResponseEntity<?> getOrdersByUserId(HttpServletRequest request);


    @Operation(summary = "Получение заказа по номеру", description = "Получение заказа по номеру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      }],
                                        "version": 0
                                      }""")))})
    @GetMapping("/{id}")
    ResponseEntity<?> getOrderById(
            @Parameter(description = "ID заказа", example = "4")
            @PathVariable Long id);

    @Operation(summary = "Обновление статуса заказа", description = "Обновление статуса заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Статус успешно изменен"))),
            @ApiResponse(responseCode = "400", description = "Неуспешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Заказ отклонен, ему нельзя поменять статус")))
    })
    @PutMapping("/privileged/update-status/{orderId}")
    ResponseEntity<?> updateOrderStatus(
            @Parameter(description = "ID заказа", example = "4")
            @PathVariable Long orderId,
            @Parameter(description = "Новый статус заказа", example = "ОТКЛОНЕН")
            @RequestParam  String status);

    @Operation(summary = "Получение списка заказов по статусу", description = "Получение списка заказов по статусу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      }],
                                        "version": 0
                                      }""")))})
    @GetMapping("/get-by-status")
    ResponseEntity<?> getOrdersByStatus(
            @Parameter(description = "Статус", example = "ОТКЛОНЕН")
            @RequestParam String status);


    @Operation(summary = "Получение списка заказов по промежутку создания", description = "Получение списка заказов по промежутку создания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "orderId": 1,
                                        "userId": 1,
                                        "orderStatus": "ОТКЛОНЕН",
                                        "totalItems": 100,
                                        "totalPrice": 39999,
                                        "createdAt": "2024-08-26T10:54:30.166223",
                                        "updatedAt": "2024-08-26T11:03:51.123996",
                                        "orderItems": [
                                          {
                                            "orderItemId": 1,
                                            "productId": 1,
                                            "quantity": 100,
                                            "price": 399.99,
                                            "version": 0
                                          }
                                        ],
                                        "version": 1
                                      }],
                                        "version": 0
                                      }""")))})
    @GetMapping("/date-range")
    ResponseEntity<?> getOrdersByDateRange(
            @Parameter(description = "Дата начала", example = "2024-08-26T00:00:00.592399")
            @RequestParam LocalDateTime startDate,
            @Parameter(description = "Дата окончания", example = "2024-08-27T00:00:00.592399")
            @RequestParam LocalDateTime endDate);
}
