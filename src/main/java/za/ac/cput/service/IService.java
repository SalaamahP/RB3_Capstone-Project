/*IService.java
IService class
Author: Salaamah Peck (230207170)
Date: 24 May 2025
*/

package za.ac.cput.service;

public interface IService <T, ID> {
    T create(T t);

    T read(ID id);

    T update(T t);

    boolean delete(ID id);
}
