package backend.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class GenericRepositoryIMPL implements IGenericRepository {
    @PersistenceContext
    private EntityManager em;
}
