package main.database.entity.global;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name= "global_place")
public class EGlobalPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "place", unique = true)
    private String place;

    public EGlobalPlace(String place){
        this.place = place;
    }
}
