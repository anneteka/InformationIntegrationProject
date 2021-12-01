package main.database.entity.global;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name= "global_author")
public class EGlobalAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "author_name")
    private String authorName;

    public EGlobalAuthor(String authorName) {
        this.authorName = authorName;
    }

}
