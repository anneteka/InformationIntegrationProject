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
@Table(name= "global_character")
public class EGlobalCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "character_name")
    private String name;

    public EGlobalCharacter(String name) {
        this.name = name;
    }

}
