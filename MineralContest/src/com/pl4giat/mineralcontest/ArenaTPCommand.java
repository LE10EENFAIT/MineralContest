package com.pl4giat.mineralcontest;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaTPCommand implements CommandExecutor {

    private final GlobalData g;
    public ArenaTPCommand(GlobalData g){
        this.g = g;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(command.getName().equalsIgnoreCase("arena") && commandSender instanceof Player &&
           g.getIs_Started() && !g.getWait_before_start() && g.getIsArenaChest()){
            RespawnRoom r = g.getRespawnRoom();
            Player p = ((Player) commandSender).getPlayer();
            if(! r.IsPlayerInRespawnRoom(p)) {
                Location loc = g.getArenaLocation();
                ((Player) commandSender).getPlayer().teleport(loc);
                return true;
            }
            return false;
        }

        return false;
    }
}
