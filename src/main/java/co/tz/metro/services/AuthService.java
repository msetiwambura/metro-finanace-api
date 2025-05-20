package co.tz.metro.services;

import co.tz.metro.data.AuthRequest;
import co.tz.metro.data.RegisterRequest;
import co.tz.metro.dto.userDtos.UserDTO;
import co.tz.metro.dto.userDtos.UserUpdateDTO;
import co.tz.metro.fusion.entity.Permission;
import co.tz.metro.fusion.entity.Role;
import co.tz.metro.fusion.entity.User;
import co.tz.metro.fusion.repository.RoleRepository;
import co.tz.metro.fusion.repository.UserRepository;
import co.tz.metro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    JwtUtil jwtUtil;


    public UserDTO registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            userRoles.add(role);
        }
        user.setRoles(userRoles);
        User cre = userRepository.save(user);
        return new UserDTO(
                cre.getId(),
                cre.getEmail(),
                cre.getFirstName(),
                cre.getMiddleName(),
                cre.getLastName(),
                cre.getRoles()
        );
    }



    public ResponseEntity<?> login(AuthRequest loginRequest) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No role assigned to user"));

        String roleName = role.getName();
        List<String> permissions = role.getPermissions().stream()
                .map(Permission::getName)
                .toList();
        String token = jwtUtil.generateUserToken(user.getEmail(), roleName , permissions);
        return ResponseEntity.ok().body(
                Map.of(
                        "token", token,
                        "role", roleName,
                        "permissions", permissions
                )
        );
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLastName(),
                        user.getRoles()
                ))
                .collect(Collectors.toList());
    }

    public User updateUser(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());

            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

}
