package com.skai.mvpassignment.model;

// TODO: 6/24/2023 ADD names (узнвть звчем(
public enum TeamStatus {
    WINNER("winners"), LOSER("losers");
    private String name;
    TeamStatus(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
