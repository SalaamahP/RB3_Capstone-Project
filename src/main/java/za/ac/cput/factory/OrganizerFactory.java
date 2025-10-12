/*OrganizerFactory.java
OrganizerFactory class
Author: OP Phologane (230690939)
Date: 17/05/2025 */

/*package za.ac.cput.factory;

import za.ac.cput.util.Helper;
import za.ac.cput.domain.Organizer;

//import java.util.Random;


public class OrganizerFactory {
    public static Organizer createOrganizer(String password, String name, String surname, String phone, String email, Organizer.OrganizerType organizerType) {
        //Long id = new Random().nextLong();
        if(Helper.isNullOrEmpty(password)){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(Helper.isNullOrEmpty(name)){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(Helper.isNullOrEmpty(surname)){
            throw new IllegalArgumentException("Surname cannot be null or empty");
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
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setPhone(phone)
                .setEmail(email)
                .setOrganizerType(organizerType)
                .build();
    }
}
*/