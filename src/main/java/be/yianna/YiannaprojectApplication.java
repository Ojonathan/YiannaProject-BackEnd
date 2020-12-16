package be.yianna;

import be.yianna.domain.*;
import be.yianna.repository.EventRepository;
import be.yianna.repository.EventTypeRepository;
import be.yianna.repository.AuthorityRepository;
import be.yianna.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class YiannaprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiannaprojectApplication.class, args);
        
    }

    @Bean
    CommandLineRunner runIt(UserRepository userRepository,
                            AuthorityRepository authorityRepository,
                            EventRepository eventRepository,
                            EventTypeRepository eventTypeRepository) {
        return args -> {
            try {
                User user1 = new User("joeyjofer08", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "https://randomuser.me/api/portraits/men/57.jpg",null);
                User user2 = new User("tiffany", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "https://randomuser.me/api/portraits/women/64.jpg",null);
                User user3 = new User("admin", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxjk1RGshxqZc8fLZv7N9Ncy21tRRKUOoaBQ&usqp=CAU",null);
                User user4 = new User("mathile09", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "https://randomuser.me/api/portraits/men/54.jpg",null);

                Authority authority1 = new Authority(AuthorityName.USER);
                Authority authority2 = new Authority(AuthorityName.ADMIN);

                user1.setAuthorities(Arrays.asList(authority1));
                user2.setAuthorities(Arrays.asList(authority1));
                user3.setAuthorities(Arrays.asList(authority2));
                user4.setAuthorities(Arrays.asList(authority1));

                authorityRepository.saveAll(Arrays.asList(authority1,authority2));
                userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

                EventType type1 = new EventType("Music");
                EventType type2 = new EventType("Art");
                EventType type3 = new EventType("Science");
                EventType type4 = new EventType("Travel");

                eventTypeRepository.saveAll(Arrays.asList(type1,type2,type3,type4));

                Event event1 = new Event("Linkin Park Concert"
                        ,"J'ai un deuxième billet pour le concert du parc Linkin à Bruxelles, quelqu'un veut y aller, les boissons sont pour moi"
                        ,"Palais 12, Bruxelles"
                        , "https://timesofsandiego.com/wp-content/uploads/2017/07/Linkin_Park_Logo_y_Mienbros.jpg"
                        ,true);
                event1.setUser(user1);
                event1.setType(type1);

                Event event2 = new Event("One Republic Concert"
                        ,"Je forme un groupe pour assister à ce concert si vous êtes intéressés écrivez-moi "
                        ,"Palais 12"
                        ,"https://s1.ticketm.net/dam/a/a9d/a44f24cb-2e36-4b99-85a3-57c9adb9da9d_1302501_RETINA_PORTRAIT_16_9.jpg"
                        , true);
                event2.setUser(user1);
                event2.setType(type1);

                Event event3 = new Event("Made in Asia"
                        ,"Qui veut faire du cosplay, j'irai avec mes amis mais si quelqu'un veut être invité"
                        ,"Tour & taxis, Bruxelles"
                        ,"https://www.madeinasia.be/wp-content/uploads/sites/62/2019/12/MIA20-Websitebanner-RGB.jpg"
                        , false);
                event3.setUser(user2);
                event3.setType(type3);

                Event event4 = new Event("Dali Exhibition"
                        ,"Ce 10 février prochain s'ouvre l'exposition d'art de Dali, qui veut venir admirer son art ?"
                        ,"Fine Arts Brussels"
                        ,"https://cartoon-productions.be/wp-content/uploads/2019/06/f39e3332e0fa56dba61554f27b890961.png"
                        , false);
                event4.setUser(user2);
                event4.setType(type2);

                Event event5 = new Event("Technopolis"
                        ,"Ce week-end, si vous voulez aller à Technopolis, il y aura un séminaire sur les nouvelles technologies, vous êtes tous invités"
                        ,"Machelen"
                        ,"https://cdn2.cheeseweb.eu/wp-content/uploads/2014/07/Entrance.jpg", true);
                event5.setUser(user4);
                event5.setType(type3);

                eventRepository.saveAll(Arrays.asList(event1, event2, event3, event4, event5));

                System.out.println("Fin de l'initialisation par CommandLineRunner ...");
            } catch (Exception ex) {
            }
        };
    }
}
