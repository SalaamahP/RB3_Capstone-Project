package za.ac.cput.service;

import za.ac.cput.domain.Rsvp;

import java.util.List;


public interface IRsvpService extends IService<Rsvp, Long> {
    List<Rsvp> getAll();

}