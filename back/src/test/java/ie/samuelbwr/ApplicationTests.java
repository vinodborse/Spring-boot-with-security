package ie.samuelbwr;

import ie.samuelbwr.user.User;
import ie.samuelbwr.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith( SpringRunner.class )
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldInitializeWithAdminUser() {
        User user = userRepository.findByUsername( "admin" );
        assertNotNull( user );
    }
}
