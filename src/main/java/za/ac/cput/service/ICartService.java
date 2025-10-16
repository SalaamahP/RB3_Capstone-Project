/* ICartService.java
 * Cart service class
 * Author: Nobahle Vuyiswa Nzimande (222641533)
 * Date: 25 May 2025
 */
package za.ac.cput.service;

import za.ac.cput.domain.Cart;
import java.util.List;

public interface ICartService extends IService<Cart, Long> {
    @Override
    Cart read(Long id); 
    
    List<Cart> getAll();
}
