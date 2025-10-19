/* IEventService.java
 * EventService class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 25 May 2025
 */
package za.ac.cput.service;

import za.ac.cput.domain.Event;

import java.util.List;

public interface IEventService {
    Event create(Event event);
    Event read(long id);
    Event update(Event event);
    boolean delete(long id);
    List<Event> getAll();
}
