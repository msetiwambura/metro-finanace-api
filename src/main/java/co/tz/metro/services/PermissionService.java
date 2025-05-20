package co.tz.metro.services;

import co.tz.metro.fusion.entity.Permission;
import co.tz.metro.fusion.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(Long id, Permission updatedPermission) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permission.setName(updatedPermission.getName());
                    permission.setDescription(updatedPermission.getDescription());
                    return permissionRepository.save(permission);
                })
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + id));
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
