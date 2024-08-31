package lebibop.catalogservice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lebibop.catalogservice.DTO.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Контроллер работы с каталогом и корзиной", description="Контроллер работы с каталогом и корзиной")
public interface CatalogAPI {
    @Operation(summary = "Поиск товаров по названию", description = "Поиск товаров по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "productId": 2,
                                            "productName": "Дискета 5,25'' в коробке",
                                            "productDescription": "Классическая дискета 5,25 дюймов в оригинальной коробке.",
                                            "productPrice": 399.99,
                                            "imageUrl": "floppy525.png",
                                            "productCategory": "МЕРЧ",
                                            "createdAt": "2024-08-26T11:21:29.718929",
                                            "productStatus": "ДОСТУПЕН",
                                            "version": 0
                                        }
                                    ]"""))),
            @ApiResponse(responseCode = "204", description = "Ничего не найдено",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Ничего не найдено по запросу: кепка")))
    })
    @GetMapping("/search")
    ResponseEntity<?> searchProductsByName(
            @Parameter(description = "Название", example = "Дискета")
            @RequestParam String name);


    @Operation(summary = "Поиск товаров по диапазону цены", description = "Поиск товаров по диапазону цены")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "productId": 2,
                                            "productName": "Дискета 5,25'' в коробке",
                                            "productDescription": "Классическая дискета 5,25 дюймов в оригинальной коробке.",
                                            "productPrice": 399.99,
                                            "imageUrl": "floppy525.png",
                                            "productCategory": "МЕРЧ",
                                            "createdAt": "2024-08-26T11:21:29.718929",
                                            "productStatus": "ДОСТУПЕН",
                                            "version": 0
                                        }
                                    ]"""))),
            @ApiResponse(responseCode = "204", description = "Неуспешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Ничего не найдено в диапазоне цен: 0.0 - 200.0")))
    })
    @GetMapping("/search/price-range")
    ResponseEntity<?> searchProductsByPriceRange(
            @Parameter(description = "Мин. цена", example = "100")
            @RequestParam Double minPrice,
            @Parameter(description = "Макс. цена", example = "2000")
            @RequestParam Double maxPrice);


    @Operation(summary = "Изменение цены товара", description = "Изменение цены товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Цена товара успешно обновлена"))),
            @ApiResponse(responseCode = "404", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар не найден"))),

    })
    @PutMapping("/privileged/update-price/{productId}")
    ResponseEntity<?> updateProductPrice(
            @Parameter(description = "Id товара", example = "2")
            @PathVariable Long productId,
            @Parameter(description = "Новая цена", example = "1000")
            @RequestParam Double newPrice);



    @Operation(summary = "Добавление товара", description = "Добавление товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар успешно добавлен")))})
    @PostMapping("/privileged/add")
    ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO);


    @Operation(summary = "Редактирование товара", description = "Редактирование товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар  не найден"))),
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар успешно обновлен"))),})
    @PutMapping("/privileged/edit/{id}")
    ResponseEntity<?> updateProduct(
            @Parameter(description = "Id товара", example = "15")
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO);

    @Operation(summary = "Удаление товара", description = "Удаление товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар успешно удален"))),
            @ApiResponse(responseCode = "404", description = "товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар не найден")))
    })
    @DeleteMapping("/privileged/delete/{id}")
    ResponseEntity<?> deleteProduct(
            @Parameter(description = "Id товара", example = "15")
            @PathVariable Long id);

    @Operation(summary = "Получение товаров опр. категории", description = "Получение товаров опр. категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                            "productId": 12,
                                                "productName": "Дискета 3,5'' в оригинальной упаковке",
                                                "productDescription": "Классическая 3,5-дюймовая дискета, сохранившаяся в идеальном состоянии.",
                                                "productPrice": 299.99,
                                                "imageUrl": "http://example.com/images/floppy35.png",
                                                "productCategory": "АНТИКВАРНЫЕ ИТ-АРТЕФАКТЫ",
                                                "createdAt": "2024-08-26T07:04:20.166792",
                                                "productStatus": "ДОСТУПЕН",
                                                "version": 0
                                        },
                                        {
                                            "productId": 13,
                                                "productName": "Перфокарта 1970-х годов",
                                                "productDescription": "Перфокарта, использовавшаяся в вычислениях на мэйнфреймах в 1970-х.",
                                                "productPrice": 499.99,
                                                "imageUrl": "http://example.com/images/punch_card.png",
                                                "productCategory": "АНТИКВАРНЫЕ ИТ-АРТЕФАКТЫ",
                                                "createdAt": "2024-08-26T07:05:11.651143",
                                                "productStatus": "ДОСТУПЕН",
                                                "version": 0
                                        }"""))),
            @ApiResponse(responseCode = "204", description = "товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Ничего не найдено по запросу: МЕРЧ")))
    })
    @GetMapping("/get-by-category")
    ResponseEntity<?> getProductsByCategory(
            @Parameter(description = "Категория", example = "МЕРЧ")
            @RequestParam String category);


    @Operation(summary = "Получение товаров опр. статуса", description = "Получение товаров опр. статуса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                                 "productId": 7,
                                                 "productName": "Футболка с логотипом",
                                                 "productDescription": "Качественная футболка с логотипом компании.",
                                                 "productPrice": 1499.99,
                                                 "imageUrl": "http://example.com/images/tshirt.png",
                                                 "productCategory": "МЕРЧ",
                                                 "createdAt": "2024-08-26T06:56:03.789658",
                                                 "productStatus": "ДОСТУПЕН",
                                                 "version": 0
                                             },
                                             {
                                                 "productId": 8,
                                                 "productName": "Кепка с эмблемой",
                                                 "productDescription": "Удобная кепка с вышитой эмблемой компании.",
                                                 "productPrice": 999.99,
                                                 "imageUrl": "http://example.com/images/cap.png",
                                                 "productCategory": "МЕРЧ",
                                                 "createdAt": "2024-08-26T07:02:15.640143",
                                                 "productStatus": "ДОСТУПЕН",
                                                 "version": 0
                                             }"""))),
            @ApiResponse(responseCode = "204", description = "товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Ничего не найдено по запросу: АРХИВ")))
    })
    @GetMapping("/status")
    ResponseEntity<?> getProductsByStatus(
            @Parameter(description = "Статус", example = "ДОСТУПЕН")
            @RequestParam String status);

    @Operation(summary = "Получение всех товаров", description = "Получение всех товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                                      "productId": 6,
                                                      "productName": null,
                                                      "productDescription": null,
                                                      "productPrice": 200.0,
                                                      "imageUrl": null,
                                                      "productCategory": null,
                                                      "createdAt": null,
                                                      "productStatus": "AAAA",
                                                      "version": 0
                                                  },
                                                  {
                                                      "productId": 7,
                                                      "productName": "Футболка с логотипом",
                                                      "productDescription": "Качественная футболка с логотипом компании.",
                                                      "productPrice": 1499.99,
                                                      "imageUrl": "http://example.com/images/tshirt.png",
                                                      "productCategory": "МЕРЧ",
                                                      "createdAt": "2024-08-26T06:56:03.789658",
                                                      "productStatus": "ДОСТУПЕН",
                                                      "version": 0
                                                  }""")))})
    @GetMapping("/get-all")
    ResponseEntity<?> getAllProducts();

    @Operation(summary = "Получение товара по id", description = "Получение товара по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар не найден"))),
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "productId": 10,
                                        "productName": "Бутылка для воды с логотипом",
                                        "productDescription": "Стильная бутылка для воды с логотипом компании.",
                                        "productPrice": 799.99,
                                        "imageUrl": "http://example.com/images/bottle.png",
                                        "productCategory": "МЕРЧ",
                                        "createdAt": "2024-08-26T07:02:54.652229",
                                        "productStatus": "ДОСТУПЕН",
                                        "version": 0
                                    }""")))})
    @GetMapping("/get-by-id/{id}")
    ResponseEntity<?> getProductById(
            @Parameter(description = "Id товара", example = "10")
            @PathVariable Long id);

    @Operation(summary = "Получение товара по id", description = "Получение товара по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар не найден"))),
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Статус успешно изменен")))})
    @PutMapping("/privileged/change-status/{id}")
    ResponseEntity<String> changeStatus(
            @Parameter(description = "Id товара", example = "10")
            @PathVariable Long id,
            @Parameter(description = "Новый статус", example = "АРХИВ")
            @RequestParam String newStatus);


    @Operation(summary = "Добавление товара в корзину", description = "Добавление товара в корзину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Товар успешно добавлен в корзину в количестве: 10 шт."))),
            @ApiResponse(responseCode = "400", description = "Неуспешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Количество товара должно быть больше 0/Этот товар находится в архиве")))
    })
    @PostMapping("/add-product/{productId}")
    ResponseEntity<?> addProductToCart(
            @Parameter(description = "Id товара", example = "10")
            @PathVariable Long productId,
            @Parameter(description = "Количество", example = "10")
            @RequestParam Integer quantity,
            HttpServletRequest request);

    @Operation(summary = "Получение корзины текущего пользователя", description = "Получение корзины текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "cartId": 12,
                                        "userId": 7,
                                        "totalItems": 130,
                                        "totalPrice": 229999.9,
                                        "cartItems": [
                                            {
                                                "cartItemId": 24,
                                                "productId": 6,
                                                "quantity": 10,
                                                "price": 200.0,
                                                "version": 0
                                            },
                                            {
                                                "cartItemId": 25,
                                                "productId": 10,
                                                "quantity": 10,
                                                "price": 799.99,
                                                "version": 0
                                            },
                                            {
                                                "cartItemId": 26,
                                                "productId": 15,
                                                "quantity": 110,
                                                "price": 2000.0,
                                                "version": 2
                                            }
                                        ],
                                        "version": 18
                                    }""")))})
    @GetMapping("/get-current-user-cart")
    ResponseEntity<?> getCart(HttpServletRequest request);


    @Operation(summary = "Получение списка категорий", description = "Получение списка категорий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        "МЕРЧ",
                                        "АНТИКВАРНЫЕ ИТ-АРТЕФАКТЫ",
                                        "ПОДАРКИ"
                                    ]""")))})
    @GetMapping("/get-categories")
    ResponseEntity<?> getCategories();

    @Operation(summary = "Получение списка статусов", description = "Получение списка статусов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        "ДОСТУПЕН",
                                        "АРХИВ"
                                    ]""")))})
    @GetMapping("/get-statuses")
    ResponseEntity<?> getStatuses();

    @Operation(summary = "Удаление некоторого количества товара из корзины", description = "Удаление некоторого количества товара из корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Количество товара изменено")))})
    @PostMapping("/remove-product/{productId}")
    ResponseEntity<?> removeProductFromCart(
            @Parameter(description = "Id товара", example = "10")
            @PathVariable Long productId,
            @Parameter(description = "Количество", example = "5")
            @RequestParam Integer quantity,
            HttpServletRequest request);

    @Operation(summary = "Удаление всего товара из корзины", description = "Удаление всего товара из корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Весь товар удален")))})
    @PostMapping("/remove-product/all/{productId}")
    ResponseEntity<?> removeAllProductFromCart(
            @Parameter(description = "Id товара", example = "10")
            @PathVariable Long productId, HttpServletRequest request);

    @Operation(summary = "Очистка корзины", description = "Очистка корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Корзина очищена")))})
    @PostMapping("/clear-cart")
    ResponseEntity<?> clearCart(HttpServletRequest request);

    @Operation(summary = "Оформление заказа", description = "Оформление заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Заказ оформлен"))),
            @ApiResponse(responseCode = "400", description = "Недоступные товары на складе/Не хватает денег",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Product ID 5 не доступен в нужном количестве. Запрошено: 100; Доступно: 0,Product ID 6 не доступен в нужном количестве. Запрошено: 100; Доступно: 0\n" +
                                    "Недостаточно средств. Требуется еще 43998.0 единиц.")))})
    @GetMapping("/send")
    ResponseEntity<?> sendCart(HttpServletRequest request);
}
