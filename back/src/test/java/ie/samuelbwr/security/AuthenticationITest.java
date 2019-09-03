package ie.samuelbwr.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldLoginWithDefaultCredentials() throws Exception {
        this.mockMvc.perform( post( "/login" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "username", "admin" )
                .param( "password", "admin" ) )
                .andExpect( status().isOk() )
                .andExpect( redirectedUrl( null ) );
    }

    @Test
    public void shouldBeAbleToLogout() throws Exception {
        MockHttpSession session = AuthenticationHelper.createAdminSession( this.mockMvc );
        this.mockMvc.perform( get( "/logout" )
                .session( session ) )
                .andExpect( status().is3xxRedirection() )
                .andExpect( redirectedUrl( "/" ) );
    }

    @Test
    public void shouldNotLoginWithInvalidCredentials() throws Exception {
        this.mockMvc.perform( post( "/login" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "username", "invalid" )
                .param( "password", "invalid" ) )
                .andExpect( status().is3xxRedirection() );
    }

    @Test
    public void shouldRedirectToLoginWhenNotLoggedAuthenticated() throws Exception {
        this.mockMvc.perform( get( "/api/todos" ) )
                .andExpect( status().is3xxRedirection() )
                .andExpect( redirectedUrl( "http://localhost/login" ) );
    }


}
