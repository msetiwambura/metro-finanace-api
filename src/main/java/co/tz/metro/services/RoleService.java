package co.tz.metro.services;

import co.tz.metro.dto.RoleDtos.RoleDTO;
import co.tz.metro.fusion.entity.Role;
import co.tz.metro.fusion.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;





    public Role addRole(RoleDTO role) {
        Role roleEntity = new Role();
        roleEntity.setName(role.getName());
        return roleRepository.save(roleEntity);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
