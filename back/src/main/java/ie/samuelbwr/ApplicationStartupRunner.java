package ie.samuelbwr;

import ie.samuelbwr.user.User;
import ie.samuelbwr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run( ApplicationArguments args ) throws Exception {
        createAdminUser();
    }

    private void createAdminUser() {
        User admin = createUser("firstName", "lastname", "admin", "admin" );
        userRepository.save( admin );
    }

    private User createUser(String firstName, String lastName, String username, String password ) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername( password );
        user.setPassword( username );
        return user;
    }
}
