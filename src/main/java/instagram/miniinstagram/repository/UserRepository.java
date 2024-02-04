package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPassword(String password);

    Optional<User> findByName(String name);

    Optional<User> findByUser(User user);
    List<User> findAll();

}
