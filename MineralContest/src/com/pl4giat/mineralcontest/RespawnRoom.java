package com.pl4giat.mineralcontest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RespawnRoom {
    private Player[] players = new Player[3];
    private int player_length = 0;

    public void addPlayer(Player p) {
        Bukkit.getConsoleSender().sendMessage(String.valueOf(player_length));
        players[player_length] = p;
        player_length = 1;
        Bukkit.getConsoleSender().sendMessage(String.valueOf(player_length));
    }
    public boolean IsPlayerInRespawnRoom(Player p){
        if(players != null) {
            for (Player player : players) {
                if (player != null && player.equals(p))
                    return true;
            }
        }
        return false;
    }


    public boolean deletePlayer(Player p) {
        int index = -1;
        for(int i = 0; i<player_length; i++){
            if(players[i].equals(p)) {
                index = i;
                break;
            }
        }


        if (players == null || index < 0) {
            return false;
        }

        boolean ret = false;

        Player[] anotherArray = new Player[player_length - 1];

        for (int i = 0, k = 0; i < player_length; i++) {

            if (i == index) {
                ret = true;
                continue;
            }

            anotherArray[k++] = players[i];
        }

        if(ret) {
            for(int i = 0; i<anotherArray.length; i++){
                players[i] = anotherArray[i];
            }

            for(int i = anotherArray.length; i<players.length; i++){
                players[i] = null;
            }
            player_length--;
        }
        return ret;
    }


}
