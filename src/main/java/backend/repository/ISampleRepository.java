package backend.repository;

import backend.model.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISampleRepository extends CrudRepository<Sample, Long> {
}
