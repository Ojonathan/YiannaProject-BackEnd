package be.yianna.configuration;

import be.yianna.domain.Role;
import be.yianna.domain.User;
import be.yianna.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User client = userRepository.findByUsername(username);
            if (client == null) {
                //LOGGER.debug("user not found with the provided username");
                return null;
            }
            //LOGGER.debug(" user from username " + client.toString());
            return new org.springframework.security.core.userdetails.User(client.getUsername(), client.getPassword(), getAuthorities(client));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user) {

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        //LOGGER.debug("user authorities are " + authorities.toString());
        return authorities;
    }


}
