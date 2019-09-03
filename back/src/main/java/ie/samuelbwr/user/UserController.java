package ie.samuelbwr.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.samuelbwr.security.AuthenticatedUser;

@RestController
@RequestMapping( "/api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
    @GetMapping
    public User getAuthenticatedUser( @AuthenticatedUser User user ) {
        return user;
    }
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @RequestMapping("/signup")
    public boolean user(@RequestParam("userName")String userName,
    		@RequestParam("password")String password,@RequestParam("firstName")String firstName,
    		@RequestParam("lastName")String lastName) {
    	
    	User user=new User();
    	
    	user.setUsername(userName);
    	user.setFirstName(firstName);
    	user.setPassword(password);
    	user.setLastName(lastName);
		userRepository.save(user);
        return true;
    }
}
