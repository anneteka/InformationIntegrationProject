package main.database.entity.global;

import lombok.*;

import javax.persistence.*;

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
    private String name;


    public EGlobalAuthor(String name) {
        this.name = name;
    }

}
