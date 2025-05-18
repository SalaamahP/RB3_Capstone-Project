/*AdminFactory.java
AdminFactory class
Author: OP Phologane (230690939)
Date: 16/05/2025
 */
package za.ac.cput.factory;
import za.ac.cput.util.Helper;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Admin.AdminRole;

import java.util.Random;

public class AdminFactory {
    public static Admin createAdmin(String password, String name, String surname, String phone, String email, AdminRole adminRole ) {
        Long id = new Random().nextLong();
        if(Helper.isNullOrEmpty(password)){
            return null;}
        if(Helper.isNullOrEmpty(name)){
            return null;
        }
        if(Helper.isNullOrEmpty(surname)){
            return null;
        }
        if(!Helper.isValidPhone(phone)){
            throw new IllegalArgumentException("Phone number is invalid");
        }
        if(!Helper.isValidEmail(email)){
            throw new IllegalArgumentException("Email is invalid");
        }
        if (adminRole ==null){
            throw new IllegalArgumentException("Admin role is empty");
        }

        return new Admin.Builder()
                .id(id)
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setPhone(phone)
                .setEmail(email)
                .setAdminRole(adminRole)
                .build();
    }
}
