package lebibop.userservice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lebibop.userservice.DTO.LoginDTO;
import lebibop.userservice.DTO.RegisterAdminDTO;
import lebibop.userservice.DTO.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name="Контроллер работы с пользователями", description="/auth/ - общий доступ; /admin/ - для пользователей с ролью ADMIN")
public interface UserAPI {
    @Operation(summary = "Регистрация", description = "Позволяет зарегистрировать пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Регистрация прошла успешно"))),
            @ApiResponse(responseCode = "400", description = "Неуспешная регистрация",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с таким именем уже существует")))})
    @PostMapping("/all/register")
    ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO);


    @Operation(summary = "Авторизация", description = "Позволяет авторизоваться пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация (Выдача токена)",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwidXNlcklkIjoxLCJyb2xlIjoiUk9MRV9LTElNIiwiaWF0IjoxNzI0MDk1MjIwLCJleHAiOjE3MjQxMzEyMjB9.ACGDyrRPF0qr9Y9VEDQ8GMvWr-TSfHtOc4MrcrCca4qJnr9xZPQ_osipwwMZkfQN9Fr6dRocQGARY292VX7Gcw"))),
            @ApiResponse(responseCode = "400", description = "Неуспешная авторизация",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Неправильный пароль/Пользователь не найден")))})
    @PostMapping("/all/login")
    ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO);



    @Operation(summary = "Регистрация админом", description = "Позволяет зарегистрировать пользователя админу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Регистрация прошла успешно"))),
            @ApiResponse(responseCode = "400", description = "Неуспешная регистрация",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с таким именем уже существует")))})
    @PostMapping("/admin/register-user")
    ResponseEntity<String> adminRegisterUser(@RequestBody RegisterAdminDTO registerAdminDTO);


    @Operation(summary = "Список пользователей", description = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "userId": 3,
                                            "username": "test",
                                            "password": "$2a$10$GQ9VPqka3rR5wva7cqeR.u73C84tq4FfSpn2ppjpvaO2mAbP8iFj.",
                                            "email": null,
                                            "firstName": null,
                                            "lastName": null,
                                            "balance": 0.0,
                                            "userRole": "ROLE_ADMIN"
                                        },
                                        {
                                            "userId": 4,
                                            "username": "test1",
                                            "password": "$2a$10$9oN.yZaMDnLyyDpTa6B1aOz.EJlIj0MTxOPnnxhuCPFYBqiVV9Vai",
                                            "email": null,
                                            "firstName": null,
                                            "lastName": null,
                                            "balance": 0.0,
                                            "userRole": "ROLE_USER"
                                        }
                                    ]""")))})
    @GetMapping("/admin/get-all")
    ResponseEntity<?> getAllUsers();


    @Operation(summary = "Список пользователей с опр. ролью", description = "Получение списка пользователей с определенной ролью")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "userId": 3,
                                            "username": "test",
                                            "password": "$2a$10$GQ9VPqka3rR5wva7cqeR.u73C84tq4FfSpn2ppjpvaO2mAbP8iFj.",
                                            "email": null,
                                            "firstName": null,
                                            "lastName": null,
                                            "balance": 0.0,
                                            "userRole": "ROLE_ADMIN"
                                        }
                                    ]""")))})
    @GetMapping("/admin/get-user-by-role/{userRole}")
    ResponseEntity<?> getUsersByRole(
            @Parameter(description = "Роль пользователя, например: ROLE_ADMIN, ROLE_MANAGER, ROLE_USER", example = "ROLE_ADMIN")
            @PathVariable String userRole);


    @Operation(
            summary = "Получение пользователя по ID",
            description = "Получение пользователя по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "userId": 3,
                                        "username": "test",
                                        "password": "$2a$10$GQ9VPqka3rR5wva7cqeR.u73C84tq4FfSpn2ppjpvaO2mAbP8iFj.",
                                        "email": null,
                                        "firstName": null,
                                        "lastName": null,
                                        "balance": 0.0,
                                        "userRole": "ROLE_ADMIN"
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Не существующий ID",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с ID 15 не найден")))})
    @GetMapping("/admin/get-by-id/{userId}")
    ResponseEntity<?> getUserById(
            @Parameter(description = "ID существующего пользователя", example = "3")
            @PathVariable Long userId);


    @Operation(summary = "Получение пользователя по никнейму", description = "Получение пользователя по никнейму")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "userId": 3,
                                        "username": "test",
                                        "password": "$2a$10$GQ9VPqka3rR5wva7cqeR.u73C84tq4FfSpn2ppjpvaO2mAbP8iFj.",
                                        "email": null,
                                        "firstName": null,
                                        "lastName": null,
                                        "balance": 0.0,
                                        "userRole": "ROLE_ADMIN"
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Не существующий ID",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь test202 не найден")))})
    @GetMapping("/admin/get-by-username/{username}")
    ResponseEntity<?> getUserByUsername(
            @Parameter(description = "ID существующего пользователя", example = "test")
            @PathVariable String username);


    @Operation(summary = "Изменение роли пользователя", description = "Изменение роли пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Роль успешно изменена"))),
            @ApiResponse(responseCode = "400", description = "Неверная роль пользователя",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Неверная роль пользователя"))),
            @ApiResponse(responseCode = "404", description = "Не существующий ID",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с ID 15 не найден")))})
    @PutMapping("/admin/update-role/{userId}")
    ResponseEntity<?> updateUserRole(
            @Parameter(description = "ID существующего пользователя", example = "test")
            @PathVariable Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Новая роль", required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "ROLE_MANAGER")))
            @RequestBody String newRole);




    @Operation(summary = "Удаление пользователя", description = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с ID 3 успешно удален"))),
            @ApiResponse(responseCode = "404", description = "Не существующий ID",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Пользователь с ID 15 не найден")))})
    @DeleteMapping("/admin/delete-user/{userId}")
    ResponseEntity<?> deleteUser(
            @Parameter(description = "ID существующего пользователя", example = "test")
            @PathVariable Long userId);


    @Operation(summary = "Получение списка ролей", description = "Получение списка ролей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    [
                                        "ROLE_ADMIN",
                                        "ROLE_MANAGER",
                                        "ROLE_USER"
                                    ]""")))})
    @GetMapping("/admin/get-roles")
    ResponseEntity<List<String>> getAllRoles();


    @Operation(summary = "Получение текущего пользователя", description = "Получение текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "userId": 3,
                                        "username": "test",
                                        "password": "$2a$10$GQ9VPqka3rR5wva7cqeR.u73C84tq4FfSpn2ppjpvaO2mAbP8iFj.",
                                        "email": null,
                                        "firstName": null,
                                        "lastName": null,
                                        "balance": 0.0,
                                        "userRole": "ROLE_USER"
                                    }""")))})
    @GetMapping("/current-user-info")
    ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request);
}
