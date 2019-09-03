package ie.samuelbwr.security;

import ie.samuelbwr.user.User;
import ie.samuelbwr.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

@RunWith( MockitoJUnitRunner.class )
@SpringBootTest
public class CustomUserDetailsServiceTest {

    private static final String USER = "test_user";
    private static final String PASS = "test_pass";

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserWithEncodedPassword() {
        doReturn( createSimpleUser() ).when( userRepository ).findByUsername( USER );
        UserDetails user = service.loadUserByUsername( USER );
        assertEquals( USER, user.getUsername() );
        assertNotNull( user.getPassword() );
    }

    @Test( expected = UsernameNotFoundException.class )
    public void shouldThrowExceptionWhenUsernameNotFound() {
        service.loadUserByUsername( "invalid" );
    }

    private User createSimpleUser() {
        User user = new User();
        user.setUsername( USER );
        user.setPassword( PASS );
        return user;
    }
}
