/* EventRepository.java
 * Event Repository class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 11 May 2025
 */
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Event;

public interface EventRepository extends JpaRepository<Event, String> {
}