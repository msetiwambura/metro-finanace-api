package co.tz.metro.dto.RoleDtos;

import java.util.Set;

public class RoleDTO {
    private String name;
    private Set<Long> permissionIds; // Add this to support assigning permissions

    public RoleDTO() {
    }

    public RoleDTO(String name, Set<Long> permissionIds) {
        this.name = name;
        this.permissionIds = permissionIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; // <-- You forgot to assign the value in your current setter
    }

    public Set<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
