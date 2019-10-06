package com.pl4giat.mineralcontest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EventStart {
    private final GlobalData g;
    EventStart(GlobalData g){
        this.g = g;
    }


    public void start() {

        if(g.getIs_Started()){
            int temp = 0;
            for (int i = 0; i < g.getTeam_tab_length(); i++) {
                for (int j = 0; j < g.getTeam_tab()[i].getPlayers_length(); j++) {
                    Player p = g.getTeam_tab()[i].getPlayer_tab()[j];
                    g.addPlayerEvent(p);
                    temp++;
                    Location loc = p.getLocation();
                    loc.setX(g.getTeam_tab()[i].getLoc()[0]);
                    loc.setY(g.getTeam_tab()[i].getLoc()[1]);
                    loc.setZ(g.getTeam_tab()[i].getLoc()[2]);
                    p.teleport(loc);
                    PlayerInventory inv = p.getInventory();
                    inv.clear();
                    inv.setArmorContents(new ItemStack[inv.getArmorContents().length]);
                    g.equip_player(p);
                }
            }
            g.clearChest();

            g.setWait_before_start(true);
            g.setTime();
            g.sendMessagePlayers_event(ChatColor.LIGHT_PURPLE + "Event is starting in " + 10 + " seconds");


            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            g.sendMessagePlayers_event(ChatColor.LIGHT_PURPLE + "Event is started!!");
                            g.setWait_before_start(false);
                            g.setSpawn_arena_chest(true);
                            g.InitTimeSpawnArenaChest();

                        }
                    },
                    g.getWait_time()*1000
            );
            Chest blue = g.getBlueChest();


        }

    }
}
