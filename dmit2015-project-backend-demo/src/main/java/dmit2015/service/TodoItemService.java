package dmit2015.service;

import common.jpa.AbstractJpaService;
import dmit2015.model.TodoItem;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TodoItemService extends AbstractJpaService {

    @Transactional
    public void createTodoItem(TodoItem newTodoItem) {
        super.create(newTodoItem);
    }

    public Optional<TodoItem> readTodoItem(Long id) {
        return super.findOptional(TodoItem.class, id);
    }

    public List<TodoItem> readAll() {
        return super.findAll(TodoItem.class);
    }

    @Transactional
    public TodoItem updateTodoItem(TodoItem existingTodoItem) {
        return super.save(existingTodoItem);
    }

    @Transactional
    public void deleteTodoItem(Long id) {
        super.deleteById(TodoItem.class, id);
    }
}
