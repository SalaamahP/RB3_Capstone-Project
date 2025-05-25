//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import za.ac.cput.domain.Notification;
import java.util.List;

public interface NotificationService {
    Notification create(Notification notification);
    Notification read(String id);
    Notification update(Notification notification);
    boolean delete(String id);
    List<Notification> getAll();
}