package backend.services.user;

import backend.model.User;
import backend.services.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService extends IGenericService<User> {
    Page<User> findByName(String name, Pageable pageable);

    Optional<User> checkExistUserName(String userName);

    Optional<User> checkExistEmail(String email);

    Optional<User> loginUser(String userName, String password);
}
