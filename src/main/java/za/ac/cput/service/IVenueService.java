package za.ac.cput.service;

import za.ac.cput.domain.Venue;

import java.util.List;

public interface IVenueService extends IService <Venue, Long>{
    List<Venue> getAll();
}
