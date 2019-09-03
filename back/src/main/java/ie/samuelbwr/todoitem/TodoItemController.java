package ie.samuelbwr.todoitem;

import ie.samuelbwr.security.AuthenticatedUser;
import ie.samuelbwr.todoitem.exceptions.TodoItemNotFoundException;
import ie.samuelbwr.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/todos" )
public class TodoItemController {

    @Autowired
    private TodoItemRepository repository;

    @Autowired
    private TodoItemMapper mapper;

    @GetMapping
    public List<TodoItem> getItems( @AuthenticatedUser User user ) {
        return repository.findAllByUser( user );
    }

    @PostMapping
    public TodoItem addItem( @AuthenticatedUser User user,
                             @RequestBody TodoItemDto todoItemDto ) {
        TodoItem item = new TodoItem();
        item.setUser( user );
        return repository.save( mapper.dtoToEntity( item, todoItemDto ) );
    }

    @PutMapping( "{itemId}" )
    public TodoItem updateItem( @PathVariable( name = "itemId" ) Long itemId,
                                @AuthenticatedUser User user,
                                @RequestBody TodoItemDto todoItemDto ) {
        TodoItem item = repository.findByIdAndUser( itemId, user )
                .orElseThrow( TodoItemNotFoundException::new );
        mapper.dtoToEntity( item, todoItemDto );
        return repository.save( item );
    }

    @DeleteMapping( "{itemId}" )
    public void deleteItem( @AuthenticatedUser User user,
                            @PathVariable( name = "itemId" ) Long itemId ) {
        repository.deleteByIdAndUser( itemId, user );
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody GenericResponse hello(){
    	return new GenericResponse("You are correct");
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody GenericResponse test(){
    	return new GenericResponse("This test for without login");
    }
}
