package be.yianna.repository;

import be.yianna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
