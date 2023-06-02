package backend.services.sample;

import backend.model.Sample;
import backend.repository.ISampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SampleServiceIMPL implements ISampleService {
    @Autowired
    private ISampleRepository sampleRepository;

    @Override
    public Iterable<Sample> findAll() {
        return sampleRepository.findAll();
    }

    @Override
    public void save(Sample sample) {
        sampleRepository.save(sample);
    }

    @Override
    public Optional<Sample> findById(Long id) {
        return sampleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        sampleRepository.deleteById(id);
    }
}
