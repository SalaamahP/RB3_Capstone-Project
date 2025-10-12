/*OrganizerServiceImpl.java
 OrganizerServiceImpl class
Author: Oratile Phologane (230690939)
Date: 25 May 2025
*/
/*package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Organizer;
import za.ac.cput.repository.OrganizerRepository;


import java.util.List;

@Service
public class OrganizerService implements IOrganizerService {
    private static IOrganizerService service;

    OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Organizer create(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public Organizer read(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }

    @Override
    public Organizer update(Organizer organizer) {
        System.out.println("Updating Organizer " + organizer);
        return this.organizerRepository.save(organizer);
    }

    @Override
    public boolean delete(Long id) {
        if (organizerRepository.existsById(id)) {
            organizerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Organizer> getAll() {
        return this.organizerRepository.findAll();
    }


}
*/