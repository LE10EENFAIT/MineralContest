package com.pl4giat.mineralcontest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.plugin2.main.server.Plugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        GlobalData g = new GlobalData();
        g.setLocations(g.getBlue_team_spawn_location(), g.getYellow_team_spawn_location(),
                g.getRed_team_spawn_location(), g.getGreen_team_spawn_location());
        getCommand("mcontest").setExecutor(new MineralContestCommands(g));
        getCommand("arena").setExecutor(new ArenaTPCommand(g));
        Bukkit.getPluginManager().registerEvents(new MineralContestListeners(g, this), this);
    }

    @Override
    public void onDisable(){

    }
}
