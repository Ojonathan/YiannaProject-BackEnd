package be.yianna.repository;

import be.yianna.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRole(String role);

}
