package ie.samuelbwr.todoitem;

import ie.samuelbwr.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    @Transactional
    void deleteByIdAndUser( Long itemId, User user );

    List<TodoItem> findAllByUser( User user );

    Optional<TodoItem> findByIdAndUser( Long itemId, User user );
}
