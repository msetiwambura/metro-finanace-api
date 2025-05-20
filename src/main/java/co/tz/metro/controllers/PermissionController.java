package co.tz.metro.controllers;

import co.tz.metro.data.ApiResponse;
import co.tz.metro.fusion.entity.Permission;
import co.tz.metro.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseEntity<Permission>>> getPermissionById(@PathVariable Long id) {
        ApiResponse<ResponseEntity<Permission>> apiResponse = new ApiResponse<>(
                true,
                "success",
                permissionService.getPermissionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()
        ));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Permission>> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(permission);
        ApiResponse<Permission> res = new ApiResponse<>(
                true,
                "success",
                createdPermission
        );
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        return ResponseEntity.ok(permissionService.updatePermission(id, permission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
