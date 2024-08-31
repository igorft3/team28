package lebibop.userservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import lebibop.userservice.DTO.LoginDTO;
import lebibop.userservice.DTO.RegisterAdminDTO;
import lebibop.userservice.DTO.RegisterDTO;
import lebibop.userservice.model.User;
import lebibop.userservice.service.UserService;
import lebibop.userservice.swagger.UserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController implements UserAPI {
    @Value("${external.service.token}")
    private String externalToken;

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Hidden
    @PostMapping("/user/check-balance")
    public ResponseEntity<?> debitUserBalance(@RequestBody Double amount, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (!externalToken.equals(request.getHeader("External-Token"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        try {
            String result = userService.debitBalance(userId, amount);
            if (result.startsWith("Недостаточно средств")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка: " + e.getMessage());
        }
    }

    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.registerUser(registerDTO);
            return ResponseEntity.ok("Регистрация прошла успешно");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка");
        }
    }

    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.loginUser(loginDTO);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка");
        }
    }


    public ResponseEntity<String> adminRegisterUser(@RequestBody RegisterAdminDTO registerAdminDTO) {
        try {
            userService.adminRegisterUser(registerAdminDTO);
            return ResponseEntity.ok("Регистрация прошла успешно");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка");
        }
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(users);
    }

    public ResponseEntity<?> getUsersByRole(@PathVariable String userRole) {
        try {
            List<User> users = userService.getUsersByRole(userRole.toUpperCase());
            return users.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка");
        }
    }

    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + userId + " не найден");
    }

    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь " + username + " не найден");
    }

    public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody String newRole) {
        try {
            userService.updateUserRole(userId, newRole);
            return ResponseEntity.ok().body("Роль успешно изменена");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Конфликт версий. Попробуйте еще раз.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка");
        }
    }

    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok().body("Пользователь с ID " + userId + " успешно удален");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Конфликт версий. Попробуйте еще раз.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при обновлении отзыва");
        }
    }

    public ResponseEntity<List<String>> getAllRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }

    public ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Optional<User> user = userService.getUserById(userId);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + userId + " не найден");
    }

    @Hidden
    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }
}
