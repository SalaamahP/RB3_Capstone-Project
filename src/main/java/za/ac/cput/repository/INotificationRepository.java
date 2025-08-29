package za.ac.cput.repository;

import za.ac.cput.domain.Notification;
import java.util.Set;

public interface INotificationRepository extends IRepository<Notification, String> {
    Set<Notification> getAll();
}
