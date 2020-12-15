package be.yianna.rest;

import be.yianna.domain.Authority;
import be.yianna.domain.AuthorityName;
import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.AuthorityRepository;
import be.yianna.repository.UserRepository;
import be.yianna.security.JwtTokenUtil;
import be.yianna.security.JwtUser;
import be.yianna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin // possible to specify settings
//@PreAuthorize("permitAll()")
public class UserRestController {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    //@RequestMapping(value = "user", method = RequestMethod.GET)
    @GetMapping("/user")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    public UserRestController(UserRepository userRepository,
                              AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository =  authorityRepository;
    }

    @PostMapping(value = "${jwt.route.registration.path}")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            User resultUser = userRepository.findByUsername(user.getUsername());

            // verify if username is already in database
            if (resultUser != null) {
                return new ResponseEntity<String>("Username already taken: ", HttpStatus.CONFLICT);

            } else {
                // - add user to Database
                Authority authority = authorityRepository.findByName(AuthorityName.USER);
                //new Role("USER", new HashSet<>(Arrays.asList(user)));
                //user.setPassword(user.getPassword());

                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setEnabled(true);
                user.setAuthorities(Arrays.asList(authority));
                userRepository.save(user);
                return new ResponseEntity<String>("Success de l'enregistrement", HttpStatus.CREATED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        try{
            User user = userRepository.findByUsername(username);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("user not found : " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{username}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getUserAvatar(@PathVariable("username") String username){
        try {
            String avatar = userRepository.getUserAvatar(username);
            return (avatar == null)?
                    new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<String>(avatar, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Error finding user avatart : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        try {
            Optional<User> resultUser = userRepository.findById(id);

            if (resultUser.isPresent()) {
                // DELETE
                userRepository.delete(resultUser.get());
                return new ResponseEntity<String>("Success de la suppresion l'enregistrement", HttpStatus.ACCEPTED);
            } else {
                // user not found
                return new ResponseEntity<String>("Username already taken: ", HttpStatus.CONFLICT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            // UPDATE
            userService.patch(user);
            return new ResponseEntity<String>("Success de la suppresion l'enregistrement", HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'enregistrement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/checklogin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
