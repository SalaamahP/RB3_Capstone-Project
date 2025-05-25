/* CartRepository.java
 * Cart repository class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 25 May 2025
 */
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {
}