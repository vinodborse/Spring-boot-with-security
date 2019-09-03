package ie.samuelbwr.todoitem;

import ie.samuelbwr.todoitem.exceptions.TodoItemNotFoundException;
import ie.samuelbwr.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith( MockitoJUnitRunner.class )
@SpringBootTest
public class TodoItemTest {

    private static final String USER = "test_user";
    private static final String PASS = "test_pass";

    @InjectMocks
    private TodoItemController controller;

    @Mock
    private TodoItemRepository repository;

    @Mock
    private TodoItemMapper mapper;

    @Captor
    private ArgumentCaptor<TodoItem> itemArgumentCaptor;

    @Before
    public void before(){
        doCallRealMethod().when( mapper ).dtoToEntity( any(),any() );
    }

    @Test
    public void shouldSetAuthenticatedUserToTodoItem() {
        controller.addItem( createSimpleUser(), createTodoItemDto( "Test", false ) );
        verify( repository ).save( itemArgumentCaptor.capture() );
        assertNotNull( itemArgumentCaptor.getValue().getUser() );
        assertEquals( USER, itemArgumentCaptor.getValue().getUser().getUsername() );
    }

    @Test
    public void shouldUpdateTodoItemValues() {
        String newText = "new text";
        String oldText = "old text";
        Long itemId = 1l;
        User user = createSimpleUser();

        doReturn( Optional.of( createTodoItem( oldText, true ) ) )
                .when( repository ).findByIdAndUser( itemId, user );

        controller.updateItem( itemId, user, createTodoItemDto(  newText, false ) );

        verify( repository ).save( itemArgumentCaptor.capture() );

        assertEquals( newText, itemArgumentCaptor.getValue().getText() );
        assertEquals( false, itemArgumentCaptor.getValue().getCompleted() );
    }

    @Test(expected = TodoItemNotFoundException.class )
    public void shouldNotUpdateOtherUsersTodoItems() {
        controller.updateItem( 2l, createSimpleUser(), createTodoItemDto(  "text", false ) );
    }

    private TodoItemDto createTodoItemDto( String text, boolean completed ) {
        TodoItemDto item = new TodoItemDto();
        item.setText( text );
        item.setCompleted( completed );
        return item;
    }
    private TodoItem createTodoItem( String text, boolean completed ) {
        TodoItem item = new TodoItem();
        item.setText( text );
        item.setCompleted( completed );
        return item;
    }

    private User createSimpleUser() {
        User user = new User();
        user.setUsername( USER );
        user.setPassword( PASS );
        return user;
    }
}
