package ie.samuelbwr.todoitem;

import org.springframework.stereotype.Component;

@Component
public class TodoItemMapper {

    public TodoItem dtoToEntity( TodoItem item, TodoItemDto dto){
        item.setText( dto.getText() );
        item.setCompleted( dto.getCompleted() );
        return item;
    }
}
