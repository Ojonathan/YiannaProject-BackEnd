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

    //@Autowired
    //private SimpMessageSendingOperations messagingTemplate;

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
                User client3 = new User("user3", "password", null);

                Role role1 = new Role("USER", new HashSet<>(Arrays.asList(client1)));
                Role role2 = new Role("ADMIN", new HashSet<>(Arrays.asList(client2,client3)));

                client1.setRoles(new HashSet<>(Arrays.asList(role1)));
                client2.setRoles(new HashSet<>(Arrays.asList(role2)));
                client3.setRoles(new HashSet<>(Arrays.asList(role2)));

                roleRepo.saveAll(Arrays.asList(role1,role2));
                userRepository.saveAll(Arrays.asList(client1, client2, client3));

                EventType type = new EventType("Musique");
                eventTypeRepository.save(type);

                Event event = new Event("Linkin Park Concert","Palais 12", false);
                event.setUser(client1);
                event.setType(type);

                Event event2 = new Event("One Republic Concert","Palais 12", true);
                event.setUser(client1);
                event.setType(type);

                Event event3 = new Event("M85 Concert","Palais 12", true);
                event.setUser(client2);
                event.setType(type);
                eventRepository.saveAll(Arrays.asList(event, event2, event3));

                System.out.println("Fin de l'initialisation par CommandLineRunner ...");
            } catch (Exception ex) {
                //LOGGER.error("Exception rencontrée lors de l'initialisation par CommandLineRunner : "+ex);
            }
        };
    }

    /**
     * Generate random numbers publish with WebSocket protocol each 3 seconds.
     * @return a command line runner.
     */
    /*@Bean
    public CommandLineRunner websocketDemo() {
        System.out.println("websocker CommandLineRunner ...");
        return (args) -> {
            while (true) {
                try {
                    Thread.sleep(3*1000); // Each 3 sec.
                    messagingTemplate.convertAndSend("/topic/progress", "Hello world");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }*/
}
