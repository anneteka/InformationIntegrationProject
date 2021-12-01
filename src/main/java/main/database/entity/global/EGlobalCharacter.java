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
@Table(name= "global_character")
public class EGlobalCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "character_name")
    private String characterName;

    public EGlobalCharacter(String characterName) {
        this.characterName = characterName;
    }

}
