package backend.services.user;

import backend.model.User;
import backend.services.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService extends IGenericService<User> {
    Page<User> findByName(String name, Pageable pageable);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<User> loginUser(String userName, String password);
}
