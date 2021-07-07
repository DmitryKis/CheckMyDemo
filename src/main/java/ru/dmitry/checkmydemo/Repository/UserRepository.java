package ru.dmitry.checkmydemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitry.checkmydemo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameOrId(String username, Long id);
    User findByUsernameOrEmail(String username, String email);
}
