package be.yianna.repository;

import be.yianna.domain.Authority;
import be.yianna.domain.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    Authority findByName(AuthorityName authorityName);

}
