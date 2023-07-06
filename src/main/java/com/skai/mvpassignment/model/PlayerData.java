package com.skai.mvpassignment.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerData {
    private String name;
    private String nick;
    private String number;
    private Integer ratingPoints;

    @Override
    public String toString() {
        return String.format("%10s %10s %10s", nick, name, ratingPoints);
    }

    public PlayerData(PlayerData playerData) {
        this(playerData.name, playerData.nick, playerData.number, playerData.ratingPoints);
    }
}
