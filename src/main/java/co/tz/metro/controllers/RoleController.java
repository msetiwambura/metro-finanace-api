package co.tz.metro.controllers;
import co.tz.metro.data.ApiResponse;
import co.tz.metro.dto.RoleDtos.RoleDTO;
import co.tz.metro.dto.RoleDtos.RoleResDTO;
import co.tz.metro.fusion.entity.Role;
import co.tz.metro.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDTO dto) {
        RoleResDTO role = roleService.addRole(dto);
        ApiResponse<RoleResDTO> res = new ApiResponse<>(
                true,
                "success",
                role
        );
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        ApiResponse<List<Role>> res = new ApiResponse<>(
                true,
                "success",
                roleService.getAllRoles()
        );
        return ResponseEntity.ok(res);
    }
}
