package co.tz.metro.fusion.repository;

import co.tz.metro.fusion.entity.Role;
import co.tz.metro.fusion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

