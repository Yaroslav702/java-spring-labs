package yaroslav.max.currencyexchangeapp.repository;

import yaroslav.max.currencyexchangeapp.entity.Role;
import yaroslav.max.currencyexchangeapp.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long create(User user);
    Optional<User> findById(Long id);
    void update(User user);
    void delete(Long id);
    List<User> findByRole(Role role);
}
