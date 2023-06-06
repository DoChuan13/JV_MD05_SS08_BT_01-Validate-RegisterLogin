package backend.repository;

import backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("select us from User as us where us.name like concat('%',:name,'%') ")
    Page<User> findByName(
            @Param("name") String name, Pageable pageable);

    @Query("select us from User as us where us.userName = :userName and us.password = :password and us.status = false ")
    Optional<User> loginUser(
            @Param("userName") String userName,
            @Param("password") String password);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
