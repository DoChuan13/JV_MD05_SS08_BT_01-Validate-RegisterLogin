package backend.services.user;

import backend.model.User;
import backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIMPL implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findByName(String name, Pageable pageable) {
        return userRepository.findByName(name, pageable);
    }

    @Override
    public Optional<User> checkExistUserName(String userName) {
        return userRepository.checkExistUserName(userName);
    }

    @Override
    public Optional<User> checkExistEmail(String email) {
        return userRepository.checkExistEmail(email);
    }

    @Override
    public Optional<User> loginUser(String userName, String password) {
        Optional<User> user = userRepository.loginUser(userName, password);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user;
            }
        }
        return Optional.empty();
    }
}
