package co.tz.metro.dto.RoleDtos;

import co.tz.metro.fusion.entity.Permission;

import java.util.Set;

public class RoleResDTO {
    private Long id;
    private String name;
    private Set<Permission> permissionIds; // Add this to support assigning permissions

    public RoleResDTO() {
    }

    public RoleResDTO(String name, Set<Permission> permissionIds, Long id) {
        this.name = name;
        this.permissionIds = permissionIds;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; // <-- You forgot to assign the value in your current setter
    }

    public Set<Permission> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Permission> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}