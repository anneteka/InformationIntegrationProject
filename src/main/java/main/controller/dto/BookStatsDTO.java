package main.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.database.entity.global.EGlobalBook;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookStatsDTO {
    private int amount;
    private List<EGlobalBook> result;
}
