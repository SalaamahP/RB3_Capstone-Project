/*Helper.java
Helper class
Author: Salaamah Peck (230207170)
Date: 18 May 2025
*/

package za.ac.cput.util;

public class Helper {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        email = email.trim();
        if (email.isEmpty())
            return false;

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null)
            return false;
        phone = phone.trim();
        if (phone.isEmpty()) return false;


        String phoneRegex = "^(\\+?[0-9]{1,3})?([0-9]{10})$";
        return phone.matches(phoneRegex);
    }

    // ---------- Event-specific Validations ----------
    public static boolean isValidVenueId(long venueId) {
        return venueId > 0;
    }

    public static boolean isValidUserId(long userId) {
        return userId > 0;
    }

    public static boolean isValidTicketPrice(double price) {
        return price >= 0;
    }

    public static boolean isValidEventCategory(Object category) {
        return category != null;
    }

    public static boolean isValidDateTime(Object dateTime) {
        return dateTime != null;
    }
}

