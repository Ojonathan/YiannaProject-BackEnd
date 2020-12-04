package be.yianna;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;
import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.EventRepository;
import be.yianna.repository.EventTypeRepository;
import be.yianna.repository.RoleRepository;
import be.yianna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@SpringBootApplication
public class YiannaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiannaprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner runIt(UserRepository userRepository,
                            RoleRepository roleRepo,
                            EventRepository eventRepository,
                            EventTypeRepository eventTypeRepository) {
        return args -> {
            try {
                User client1 = new User("user1", "password", null);
                User client2 = new User("user2", "password", null);
                User client4 = new User("user4", "password", null);
                User client3 = new User("user3", "password", null);

                Role role1 = new Role("USER", new HashSet<>(Arrays.asList(client1, client2, client4)));
                Role role2 = new Role("ADMIN", new HashSet<>(Arrays.asList(client3)));

                client1.setRoles(new HashSet<>(Arrays.asList(role1)));
                client2.setRoles(new HashSet<>(Arrays.asList(role2)));
                client3.setRoles(new HashSet<>(Arrays.asList(role2)));
                client4.setRoles(new HashSet<>(Arrays.asList(role1)));

                roleRepo.saveAll(Arrays.asList(role1,role2));
                userRepository.saveAll(Arrays.asList(client1, client2, client3, client4));

                EventType type1 = new EventType("Music");
                EventType type2 = new EventType("Art");
                EventType type3 = new EventType("Science");
                eventTypeRepository.saveAll(Arrays.asList(type1,type2,type3));

                Event event1 = new Event("Linkin Park Concert","Palais 12", true);
                event1.setUser(client1);
                event1.setType(type1);

                Event event2 = new Event("One Republic Concert","Palais 12", true);
                event2.setUser(client1);
                event2.setType(type1);

                Event event3 = new Event("Lab Expo","Tour and taxis ", false);
                event3.setUser(client2);
                event3.setType(type3);

                Event event4 = new Event("Japan Art","Fine Arts Brussels", false);
                event4.setUser(client2);
                event4.setType(type2);

                Event event5 = new Event("Technopolis","Machelen", true);
                event5.setUser(client4);
                event5.setType(type3);

                eventRepository.saveAll(Arrays.asList(event1, event2, event3, event4, event5));

                System.out.println("Fin de l'initialisation par CommandLineRunner ...");
            } catch (Exception ex) {
                //LOGGER.error("Exception rencontrée lors de l'initialisation par CommandLineRunner : "+ex);
            }
        };
    }
}
