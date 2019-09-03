package ie.samuelbwr.security;

import ie.samuelbwr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return Optional.ofNullable( this.repository.findByUsername( username ))
                .orElseThrow( () -> new UsernameNotFoundException( "This username is not registered." ) );
    }
}