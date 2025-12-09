package yaroslav.max.currencyexchangeapp.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import yaroslav.max.currencyexchangeapp.entity.Role;
import yaroslav.max.currencyexchangeapp.entity.User;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class UserRepositoryJdbcClient implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepositoryJdbcClient(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("INSERT INTO users (username, password, role) VALUES (:username, :password, :role)")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("role", user.getRole().name())
                .update(keyHolder, "id");

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM users WHERE id = :id")
                .param("id", id)
                .query((rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                ))
                .optional();
    }

    @Override
    public void update(User user) {
        jdbcClient.sql("UPDATE users SET username = :username, password = :password, role = :role WHERE id = :id")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("role", user.getRole().name())
                .param("id", user.getId())
                .update();
    }

    @Override
    public void delete(Long id) {
        jdbcClient.sql("DELETE FROM users WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public List<User> findByRole(Role role) {
        return jdbcClient.sql("SELECT * FROM users WHERE role = :role")
                .param("role", role.name())
                .query((rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                ))
                .list();
    }
}
