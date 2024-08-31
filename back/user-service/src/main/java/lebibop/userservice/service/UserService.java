package lebibop.userservice.service;

import lebibop.userservice.enums.UserRole;
import lebibop.userservice.DTO.LoginDTO;
import lebibop.userservice.DTO.RegisterAdminDTO;
import lebibop.userservice.DTO.RegisterDTO;
import lebibop.userservice.model.User;
import lebibop.userservice.repository.UserRepository;
import lebibop.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder  passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,@Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String debitBalance(Long userId, Double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));

        if (user.getBalance() < amount) {
            return "Недостаточно средств. Требуется еще " + (amount - user.getBalance()) + " единиц.";
        } else {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            return "Операция успешна.";
        }
    }

    @Transactional
    public void updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
        String normalizedRole = newRole.toUpperCase();
        user.setUserRole(normalizedRole);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
        userRepository.delete(user);
    }

    @Transactional
    public void registerUser(RegisterDTO registerDTO) {
        validateAndSaveUser(registerDTO.toUser());
    }

    @Transactional
    public void adminRegisterUser(RegisterAdminDTO registerAdminDTO) {
        validateAndSaveUser(registerAdminDTO.toUser());
    }

    @Transactional
    protected void validateAndSaveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public String loginUser(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            UserDetails userDetails = loadUserByUsername(loginDTO.getUsername());
            return jwtUtil.generateToken(userDetails.getUsername(), user.getUserId(), user.getUserRole());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный пароль");
        }
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRole(String role) {
        return userRepository.findByUserRole(role);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUserRole())
                .build();
    }

    public List<String> getRoles() {
        return Arrays.stream(UserRole.values())
                .map(UserRole::getDisplayName)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addPrice(Long userId, Double price) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBalance(user.getBalance() + price);
            userRepository.save(user);
        }
    }
}
