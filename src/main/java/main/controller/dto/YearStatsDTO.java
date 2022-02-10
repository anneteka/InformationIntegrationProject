package main.controller.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class YearStatsDTO {
    Integer year;
    Integer amount;
}
