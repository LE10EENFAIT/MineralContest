package com.pl4giat.mineralcontest;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnPlayer extends BukkitRunnable {

    private final GlobalData g;
    private final Player p;
    public RespawnPlayer(GlobalData g, Player p){
        this.g = g;
        this.p = p;
    }

    @Override
    public void run() {
        g.equip_player(p);
        String color = g.getTeamColorByPlayer(p);
        Location loc = p.getLocation();
        loc.setX(g.getSpawn_location_by_color(color)[0]);
        loc.setY(g.getSpawn_location_by_color(color)[1]);
        loc.setZ(g.getSpawn_location_by_color(color)[2]);
        p.teleport(loc);
        RespawnRoom r = g.getRespawnRoom();
        r.deletePlayer(p);
    }
}
