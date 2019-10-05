package com.pl4giat.mineralcontest;

import org.bukkit.entity.Player;


public class Team {
    private Player[] player_tab = new Player[4];
    private int players_length = 0;
    private double[] loc = null;
    private String color = null;
    private int score = 0;

    public void addPlayer_tab(Player p){
        player_tab[players_length] = p;
        players_length++;
    }
    public int getPlayers_length(){
        return players_length;
    }
    public void setLocation(double[] l){
        loc = l;
    }
    public void setColor(String c){
        color = c;
    }
    public void addScore(int s){
        score += s;
    }
    public Player[] getPlayer_tab(){
        return player_tab;
    }
    public double[] getLoc(){ return loc; }
    public String getColor(){
        return color;
    }
    public int getScore(){
        return score;
    }

    public boolean isPlayer(Player player){
        for(int i = 0; i < players_length; i++){
            if(player_tab[i] == player)
                return true;
        }
        return false;
    }

}
