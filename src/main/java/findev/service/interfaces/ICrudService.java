package findev.service.interfaces;

import java.util.List;

public interface ICrudService<T> {
    boolean isExists(Long id);

    T getById(Long id);

    T save(T t);

    void delete(Long id);

    List<T> getAll();

}
