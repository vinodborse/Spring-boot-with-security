package ie.samuelbwr.todoitem;

import ie.samuelbwr.security.AuthenticationHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemITest {

    private static final String TODO_TEXT = "Test";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Before
    public void after() {
        todoItemRepository.deleteAll();
    }

    @Test
    public void shouldAddTodoItem() throws Exception {
        MockHttpSession session = AuthenticationHelper.createAdminSession( mockMvc );
        createTodo( session ).andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id", equalTo( 1 ) ) )
                .andExpect( jsonPath( "$.text", equalTo( TODO_TEXT ) ) )
                .andExpect( jsonPath( "$.completed", equalTo( false ) ) )
                .andExpect( jsonPath( "$.lastUpdate", notNullValue() ) );
    }

    @Test
    public void shouldDeleteTodoItem() throws Exception {
        MockHttpSession session = AuthenticationHelper.createAdminSession( mockMvc );
        createTodo( session );
        this.mockMvc.perform( delete( "/api/todos/" + 2 )
                .session( session ) )
                .andExpect( status().isOk() );
        TodoItem item = todoItemRepository.findById( 2L ).orElse( null );
        Assert.assertNull( item );
    }

    @Test
    public void shouldGetAllTodoItem() throws Exception {
        MockHttpSession session = AuthenticationHelper.createAdminSession( mockMvc );
        createTodo( session );
        createTodo( session );
        this.mockMvc.perform( get( "/api/todos" )
                .session( session ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$", hasSize( 2 ) ) );
    }

    @Test
    public void shouldUpdateTodoItem() throws Exception {
        String updatedText = "Updated text";
        Integer id = 2;
        MockHttpSession session = AuthenticationHelper.createAdminSession( mockMvc );
        createTodo( session );
        this.mockMvc.perform( put( "/api/todos/" + id )
                .contentType( MediaType.APPLICATION_JSON )
                .content( getTodoItemAsJson( updatedText, true ) )
                .session( session ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id", equalTo( id ) ) )
                .andExpect( jsonPath( "$.text", equalTo( updatedText ) ) )
                .andExpect( jsonPath( "$.completed", equalTo( true ) ) )
                .andExpect( jsonPath( "$.lastUpdate", notNullValue() ) );
    }

    private String getTodoItemAsJson( String text, boolean completed ) {
        return "{\"text\":\"" + text + "\", \"completed\":" + completed + "}";
    }

    private ResultActions createTodo( MockHttpSession session ) throws Exception {
        return this.mockMvc.perform( post( "/api/todos" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( getTodoItemAsJson( TODO_TEXT, false ) )
                .session( session ) );
    }
}
