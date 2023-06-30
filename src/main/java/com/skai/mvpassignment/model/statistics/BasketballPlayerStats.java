package com.skai.mvpassignment.model.statistics;

import com.opencsv.bean.CsvBindByPosition;
import com.skai.mvpassignment.model.GameData;
import com.skai.mvpassignment.model.PlayerData;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketballPlayerStats implements PlayerStats {
    @CsvBindByPosition(position = 0, required = true)
    private String name;
    @CsvBindByPosition(position = 1, required = true)
    private String nick;
    @CsvBindByPosition(position = 2, required = true)
    private String number;
    @CsvBindByPosition(position = 3, required = true)
    private String teamName;
    @CsvBindByPosition(position = 4, required = true)
    private Integer scoredPoints;
    @CsvBindByPosition(position = 5, required = true)
    private Integer rebounds;
    @CsvBindByPosition(position = 6, required = true)
    private Integer assists;

}
