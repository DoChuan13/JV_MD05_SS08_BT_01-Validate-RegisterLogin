package backend.services;

import java.util.Optional;

public interface IGenericService<T> {
    Iterable<T> findAll();

    void save(T t);

    Optional<T> findById(Long id);

    void deleteById(Long id);
}
