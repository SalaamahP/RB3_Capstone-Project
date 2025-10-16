package za.ac.cput.service;

import za.ac.cput.domain.Role;

import java.util.List;

public interface IRoleService extends IService <Role, Long>{
    List<Role> getAll();
    Role findByRoleName(String roleName);
}
