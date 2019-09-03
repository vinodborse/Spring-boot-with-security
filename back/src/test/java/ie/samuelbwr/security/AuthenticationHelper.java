package ie.samuelbwr.security;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationHelper {
    public static final MockHttpSession createAdminSession( MockMvc mockMvc) throws Exception {
        return ( MockHttpSession ) mockMvc.perform( post( "/login" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "username", "admin" )
                .param( "password", "admin" ) )
                .andExpect( status().isOk() )
                .andReturn().getRequest().getSession();
    }
}
