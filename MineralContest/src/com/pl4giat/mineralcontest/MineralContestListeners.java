package com.pl4giat.mineralcontest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;

public class MineralContestListeners implements Listener {

    private final GlobalData g;
    private final Main plugin;
    MineralContestListeners(GlobalData g, Main plugin){
        this.g = g;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Location loc = new Location(p.getWorld(), g.getSpawn_location()[0], g.getSpawn_location()[1], g.getSpawn_location()[2]);
        p.teleport(loc);
    }

    @EventHandler
    public void onWaitingTime(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(g.getWait_before_start() && g.getIs_Started()) {
            p.setFoodLevel(20);
            String color = g.getTeamColorByPlayer(p);
            Location loc = p.getLocation();

            if(loc.getX() > g.getSpawn_location_by_color(color)[0] ||
               loc.getX() < g.getSpawn_location_by_color(color)[0] ||
               loc.getY() < g.getSpawn_location_by_color(color)[1] ||
               loc.getZ() < g.getSpawn_location_by_color(color)[2] ||
               loc.getZ() > g.getSpawn_location_by_color(color)[2]) {

                loc.setX(g.getSpawn_location_by_color(color)[0]);
                loc.setY(g.getSpawn_location_by_color(color)[1]);
                loc.setZ(g.getSpawn_location_by_color(color)[2]);
                p.teleport(loc);
            }
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(g.getIs_Started() && !g.getWait_before_start()) {
            final Player p = e.getPlayer();
            e.setRespawnLocation(g.getCooldown_respawn_location());
            RespawnRoom room = g.getRespawnRoom();
            room.addPlayer(p);
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Respawn in 10 seconds.");
            BukkitRunnable respawn = new RespawnPlayer(g, p);
            respawn.runTaskLater(plugin, 20*10);
        }
    }

    @EventHandler
    public void addArenaChest(PlayerMoveEvent e){
        if(g.getIs_Started() && !g.getWait_before_start() && g.getTime_spawn_arena_chest()[g.getNbArenaChest()] == g.getElapsedTime()) {
            if(!g.getIsArenaChest()) {
                g.add_arena_chest();
                g.sendMessagePlayers_event(ChatColor.RED + "A chest has appeared in the arena! " + ChatColor.RED + "/arena to tp in the arena.");
                g.setNbArenaChest(g.getNbArenaChest() + 1);
            } else {
                g.setNbArenaChest(g.getNbArenaChest() + 1);
            }
        }
    }

    @EventHandler
    public void CheckArenaChestOnOpen(InventoryOpenEvent e){
        if(g.getIsArenaChest()){
            Chest actual_chest = null;
            Chest chest_arena = g.getArenaChest();
            if(e.getInventory().getHolder() instanceof Chest) {
                actual_chest = (Chest) e.getInventory().getHolder();
            }
            if (actual_chest != null && actual_chest.equals(chest_arena)){
                g.sendMessagePlayers_event(ChatColor.RED + "The arena chest has been open!!");
            }
        }
    }

    @EventHandler
    public void CheckArenaChestOnClose(InventoryCloseEvent e){
        if(g.getIsArenaChest()) {
            Chest actual_chest = null;
            Chest chest_arena = g.getArenaChest();
            if(e.getInventory().getHolder() instanceof Chest) {
                 actual_chest = (Chest) e.getInventory().getHolder();
            }
            if (actual_chest != null && actual_chest.equals(chest_arena)) {
                boolean is_empty = true;
                for (ItemStack item : chest_arena.getBlockInventory()) {
                    if (item != null) {
                        is_empty = false;
                        break;
                    }
                }
                if (is_empty) {
                    g.remove_arena_chest();
                    g.sendMessagePlayers_event(ChatColor.LIGHT_PURPLE + "The is now empty. You cannot /arena anymore.");
                }
            }
        }
    }


    @EventHandler
    public void CheckTeamChest(InventoryCloseEvent e){
        if(g.getIs_Started() && !g.getWait_before_start()) {
            Chest actual_chest = null;
            Chest blue_chest = g.getBlueChest();
            Chest yellow_chest = g.getYellowChest();
            Chest red_chest = g.getRedChest();
            Chest green_chest = g.getGreenChest();
            String color = "";

            if (e.getInventory().getHolder() instanceof Chest) {
                actual_chest = (Chest) e.getInventory().getHolder();
            }
            if (actual_chest != null && actual_chest.equals(blue_chest))
                color = "blue";
            if (actual_chest != null && actual_chest.equals(yellow_chest))
                color = "yellow";
            if (actual_chest != null && actual_chest.equals(red_chest))
                color = "red";
            if (actual_chest != null && actual_chest.equals(green_chest))
                color = "green";

            if (actual_chest != null && !color.equals("")) {
                int points = g.getChestPoints(actual_chest, color);
                Player[] players = g.getPlayersByTeamColor(color);
                for (Player p : players) {
                    if(p != null)
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Your team has " + points + " points.");
                }
                ChatColor chatColor = null;
                switch (color) {
                    case "blue":
                        chatColor = ChatColor.BLUE;
                        break;
                    case "yellow":
                        chatColor = ChatColor.YELLOW;
                        break;
                    case "red":
                        chatColor = ChatColor.RED;
                        break;
                    case "green":
                        chatColor = ChatColor.GREEN;
                        break;
                }
                Bukkit.getConsoleSender().sendMessage("The " + chatColor + color + ChatColor.WHITE + " team has " + points + " points.");
            }
        }

    }


    @EventHandler
    public void endEvent(PlayerMoveEvent e){
        if(g.getIs_Started() && g.getElapsedTime() >= g.getEnding_time() - g.getStarting_time()){
            boolean through = true;
            int max = 0;
            int indice = 0;
            Chest[] teamchest = {g.getBlueChest(), g.getYellowChest(), g.getRedChest(), g.getGreenChest()};
            String[] colors = {"blue", "yellow", "red", "green"};
            int[] points = {g.getChestPoints(teamchest[0], colors[0]), g.getChestPoints(teamchest[1], colors[1]),
                            g.getChestPoints(teamchest[2], colors[2]), g.getChestPoints(teamchest[3], colors[3])};
            for(int i = 0; i<4; i++){
                ChatColor chatColor = null;
                switch (colors[i]) {
                    case "blue":
                        chatColor = ChatColor.BLUE;
                        break;
                    case "yellow":
                        chatColor = ChatColor.YELLOW;
                        break;
                    case "red":
                        chatColor = ChatColor.RED;
                        break;
                    case "green":
                        chatColor = ChatColor.GREEN;
                        break;
                }
                g.sendMessagePlayers_event( "Team " + chatColor + colors[i] + " : " + points[i]);
                if(points[i] > max || through){
                    through = false;
                    max = points[i];
                    indice = i;
                }
            }

            ChatColor chatColor = null;
            switch (colors[indice]) {
                case "blue":
                    chatColor = ChatColor.BLUE;
                    break;
                case "yellow":
                    chatColor = ChatColor.YELLOW;
                    break;
                case "red":
                    chatColor = ChatColor.RED;
                    break;
                case "green":
                    chatColor = ChatColor.GREEN;
                    break;
            }
            g.sendMessagePlayers_event( "The team " + chatColor + colors[indice] + ChatColor.WHITE + " has won!!");
            g.reset();
        }
    }

    @EventHandler
    public void printTime(PlayerMoveEvent e){
        if(g.getIs_Started() && g.getElapsedTime() % (g.getModulo() * 60) == 0 &&
           g.getTimeRemaining() != (int) ((g.getEnding_time() - g.getStarting_time()) - g.getElapsedTime()) &&
           g.getTimeRemaining() != 0 && !g.getWait_before_start()){
            g.setTimeRemaining((int) ((g.getEnding_time() - g.getStarting_time()) - g.getElapsedTime()));
            g.sendMessagePlayers_event(ChatColor.LIGHT_PURPLE + "Time remaining : " + ((g.getEnding_time() - g.getStarting_time()) - g.getElapsedTime())/60 + " minutes.");
            if(((g.getEnding_time() - g.getStarting_time()) - g.getElapsedTime())/60 == 10)
                g.setModulo(5);
        }
    }

    @EventHandler
    public void fillfood(PlayerMoveEvent e){
        if(g.getIs_Started()){
            Player player = e.getPlayer();
            player.setFoodLevel(20);
        }
    }

}
