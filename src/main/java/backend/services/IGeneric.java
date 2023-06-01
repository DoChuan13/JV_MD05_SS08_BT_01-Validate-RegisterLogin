package backend.services;

import java.util.List;

public interface IGeneric<T> {
    List<T> findAll();

    void save(T t);

    T findById(Long id);

    void deleteById(Long id);
}
