package co.tz.metro.services;

import co.tz.metro.dto.RoleDtos.RoleDTO;
import co.tz.metro.dto.RoleDtos.RoleResDTO;
import co.tz.metro.dto.userDtos.UserDTO;
import co.tz.metro.fusion.entity.Permission;
import co.tz.metro.fusion.entity.Role;
import co.tz.metro.fusion.entity.User;
import co.tz.metro.fusion.repository.PermissionRepository;
import co.tz.metro.fusion.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;





    public RoleResDTO addRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        Set<Permission> permissionSet = new HashSet<>();
        System.out.println("permissionSet: Ids" + roleDTO.getPermissionIds());

        for (Long permissionId : roleDTO.getPermissionIds()) {
            Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new RuntimeException("Permission not found"));
            permissionSet.add(permission);
        }
        role.setPermissions(permissionSet);

        Role rl =  roleRepository.save(role);
        return new RoleResDTO(
                rl.getName(),
                rl.getPermissions(),
                rl.getId()
        );



    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
