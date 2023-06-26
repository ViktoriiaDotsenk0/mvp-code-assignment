package com.skai.mvpassignment.model.statistics;

import com.opencsv.bean.CsvBindByPosition;
import com.skai.mvpassignment.model.PlayerData;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandballPlayerStats implements PlayerStats {
    @CsvBindByPosition(position = 0, required = true)
    private String name;
    @CsvBindByPosition(position = 1, required = true)
    private String nick;
    @CsvBindByPosition(position = 2, required = true)
    private String number;
    @CsvBindByPosition(position = 3, required = true)
    private String teamName;
    @CsvBindByPosition(position = 4, required = true)
    private Integer goalsMade;
    @CsvBindByPosition(position = 5, required = true)
    private Integer goalsReceived;
    private PlayerData playerData;

    @Override
    public PlayerData getPlayerData() {
        if (playerData == null)
            playerData = PlayerData.builder()
                    .name(name)
                    .nick(nick)
                    .number(number)
                    .ratingPoints(0)
                    .build();
        return playerData;
    }
}
