/*OrganizerFactory.java
OrganizerFactory class
Author: OP Phologane (230690939)
Date: 17/05/2025 */

package za.ac.cput.factory;

import za.ac.cput.Util.Helper;
import za.ac.cput.domain.Organizer;

import java.util.Random;

public class OrganizerFactory {
    public static Organizer createOrganizer(String password, String name, String surname, String phone, String email, Organizer.OrganizerType organizerType) {
        Long id = new Random().nextLong();
        if(Helper.isNullOrEmpty(password)){
            return null;
        }
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
        if (organizerType ==null){
            throw new IllegalArgumentException("Organizer type is empty");
        }

        return new Organizer.Builder()
                .id(id)
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setPhone(phone)
                .setEmail(email)
                .setOrganizerType(organizerType)
                .build();
    }
}
