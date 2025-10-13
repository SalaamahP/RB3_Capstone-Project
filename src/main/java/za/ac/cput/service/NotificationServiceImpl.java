//[author] Jaedon Prince, 230473474
//[date] 25/05/2025
package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Notification;
import za.ac.cput.repository.NotificationRepository;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public Notification read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Notification update(Notification notification) {
        if (repository.existsById(notification.getNotificationID())) {
            return repository.save(notification);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Notification> getAll() {
        return repository.findAll();
    }
}

