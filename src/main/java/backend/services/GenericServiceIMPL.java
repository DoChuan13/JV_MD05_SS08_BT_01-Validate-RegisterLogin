package backend.services;

import backend.repository.IGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericServiceIMPL implements IGenericService {
    @Autowired
    private IGenericRepository genericRepository;
}
