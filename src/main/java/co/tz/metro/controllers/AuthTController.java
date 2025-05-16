package co.tz.metro.controllers;

import co.tz.metro.data.ApiResponse;
import co.tz.metro.data.AuthRequest;
import co.tz.metro.data.RegisterRequest;
import co.tz.metro.dto.userDtos.UserDTO;
import co.tz.metro.dto.userDtos.UserUpdateDTO;
import co.tz.metro.fusion.entity.User;
import co.tz.metro.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class AuthTController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        ApiResponse response = new ApiResponse(
                true,
                "success",
                authService.registerUser(request)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsers() {
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(
                true,
                "success",
                authService.getAllUsers()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {

        ApiResponse<User> response = new ApiResponse<>(
                true,
                "success",
                authService.updateUser(id, dto)
        );
        return ResponseEntity.ok(response);
    }
}
