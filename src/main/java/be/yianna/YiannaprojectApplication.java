package be.yianna;

import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.RoleRepository;
import be.yianna.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class YiannaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiannaprojectApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder encoder() {
        return 	new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runIt(UserRepository userRepository,
                            RoleRepository roleRepo) {
        return args -> {
            try {
                User client1 = new User("user1", encoder().encode("password"), null);
                Role role1 = new Role("USER", new HashSet<>(Arrays.asList(client1)));
                client1.setRoles(new HashSet<>(Arrays.asList(role1)));

                User client2 = new User("user2", encoder().encode("password"), null);
                Role role2 = new Role("ADMIN", new HashSet<>(Arrays.asList(client2)));
                client2.setRoles(new HashSet<>(Arrays.asList(role2)));

                roleRepo.saveAll(Arrays.asList(role1,role2));

                userRepository.saveAll(Arrays.asList(client1, client2));

                System.out.println("Fin de l'initialisation par CommandLineRunner ...");
            } catch (Exception ex) {
                //LOGGER.error("Exception rencontrée lors de l'initialisation par CommandLineRunner : "+ex);
            }
        };
    }

}
