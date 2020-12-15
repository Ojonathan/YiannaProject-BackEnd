package be.yianna.repository;

import be.yianna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//TODO Documentation
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    @Query("select u.avatar from User u where u.username = :username")
    String getUserAvatar(@Param(value = "username") String username);

}
