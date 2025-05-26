/*OrganizerServiceImpl.java
 OrganizerServiceImpl class
Author: Oratile Phologane (230690939)
Date: 25 May 2025
*/
package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Organizer;
import za.ac.cput.repository.OrganizerRepository;


import java.util.List;
import java.util.Optional;

@Service
public abstract class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Organizer create(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public Organizer read(Long id) {
        Optional<Organizer> organizer = organizerRepository.findById(id);
        return organizer.orElse(null);
    }

    @Override
    public Organizer update(Organizer organizer) {
        if (organizerRepository.existsById(organizer.getId())) {
            return organizerRepository.save(organizer);
        }
        return null;
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
        return (List<Organizer>) organizerRepository.findAll();
    }


}
