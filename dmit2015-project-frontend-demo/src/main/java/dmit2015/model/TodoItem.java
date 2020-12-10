package dmit2015.model;

import lombok.Data;

@Data
public class TodoItem {

    private Long id;

    private String name;

    private boolean complete;

}
