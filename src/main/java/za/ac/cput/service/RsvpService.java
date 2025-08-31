package za.ac.cput.service;

import za.ac.cput.domain.Rsvp;

import java.util.List;

public interface RsvpService extends IService<Rsvp, String> {
    List<Rsvp> getAll();

}