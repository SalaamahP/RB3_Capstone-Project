package za.ac.cput.factory;

import za.ac.cput.domain.Role;
import za.ac.cput.util.Helper;

public class RoleFactory {
    public static Role createRole(String roleName, String description) {
        if (Helper.isNullOrEmpty(roleName)) {
            throw new IllegalArgumentException(" Role name cannot be null or empty");
        }
        if (Helper.isNullOrEmpty(description)) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        return new Role.Builder()
                .setRoleName(roleName)
                .setDescription(description)
                .build();
    }
}
