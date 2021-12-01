package main.database.entity.global;

import lombok.*;

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
@Table(name= "global_genre")
public class EGlobalGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "genre")
    private String genre;

    public EGlobalGenre(String genre) {
        this.genre = genre;
    }

}
