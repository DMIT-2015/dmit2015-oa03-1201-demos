package dmit2015.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class TodoItem {

    @Id                 // This is the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // This primary key field is generated by the database
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    private boolean complete;

}
