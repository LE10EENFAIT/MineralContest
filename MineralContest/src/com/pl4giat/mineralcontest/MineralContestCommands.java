package com.pl4giat.mineralcontest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.Bukkit.getPlayer;

public class MineralContestCommands implements CommandExecutor {

    private final GlobalData g;

    MineralContestCommands(GlobalData g) {
        this.g = g;
    }

    String[] colors = {"blue", "yellow", "red", "green"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player && !commandSender.isOp() && !command.getName().equalsIgnoreCase("arena")) {
            commandSender.sendMessage("This command can only be executed by server operator.");
            return true;
        }

        if (args.length == 0 || args.length > 3)
            return false;


        if (command.getName().equalsIgnoreCase("mcontest")) {
            if (!g.getIs_Started()) {
                if (args.length == 2) {
                    if (args[0].equals("nbteam") && Integer.parseInt(args[1]) <= 4) {
                        if (Integer.parseInt(args[1]) <= 1) {
                            commandSender.sendMessage("Not enough teams to begin (min. 2).");
                            return true;
                        }

                        if (g.getTeam_tab_length() + Integer.parseInt(args[1]) > 4) {
                            commandSender.sendMessage("Max 4 teams.");
                            return true;
                        }

                        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                            Team t = new Team();
                            t.setColor(colors[i]);
                            t.setLocation(g.getSpawn_location_by_color(colors[i]));
                            g.addTeam_tab(t);
                        }
                        return true;
                    }
                    return false;
                }

                if (args.length == 3) {
                    if (args[0].equals("addteam")) {
                        if (Integer.parseInt(args[1]) > g.getTeam_tab_length() || Integer.parseInt(args[1]) <= 0) {
                            commandSender.sendMessage("The team no : " + args[1] + " doesn't exists");
                            return true;
                        }

                        Player p = getPlayer(args[2]);
                        if (!g.getTeamColorByPlayer(p).equals("noteam")) {
                            commandSender.sendMessage(args[2] + " is already the " + g.getTeamColorByPlayer(p) + " team.");
                            return true;
                        } else if (g.getTeam_tab()[Integer.parseInt(args[1]) - 1].getPlayers_length() + 1 > 4) {
                            commandSender.sendMessage("Max 4 players per team.");
                            return true;
                        } else {
                            if (p != null) {
                                g.getTeam_tab()[Integer.parseInt(args[1]) - 1].addPlayer_tab(p);
                                String color = g.getTeam_tab()[Integer.parseInt(args[1]) - 1].getColor();
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
                                p.sendMessage("You are in " + chatColor + color + ChatColor.WHITE + " team.");
                            } else
                                commandSender.sendMessage(args[2] + " is not connected on the server.");
                            return true;
                        }
                    }
                }
                if (args[0].equals("start") && args.length == 1) {
                    if (g.getTeam_tab_length() <= 0) {
                        commandSender.sendMessage("Not enough teams to begin (min. 2).");
                        return true;
                    }

                /*for(int i = 0; i<g.getTeam_tab_length(); i++){
                    if(g.getTeam_tab()[i].getPlayers_length() < 1){
                        commandSender.sendMessage("Not enough players in team no : " + (i+1) + ".");
                        return true;
                    }
                }*/
                    g.setIs_Started(true);
                    EventStart e = new EventStart(g);

                    e.start();

                    return true;
                }

                if (args[0].equals("print")) {
                    commandSender.sendMessage("number of team : " + g.getTeam_tab_length());
                    for (int i = 0; i < g.getTeam_tab_length(); i++) {
                        commandSender.sendMessage("Color of team " + (i + 1) + " : " + g.getTeam_tab()[i].getColor());
                        for (int j = 0; j < g.getTeam_tab()[i].getPlayers_length(); j++) {
                            commandSender.sendMessage("Player " + (j + 1) + " : " + g.getTeam_tab()[i].getPlayer_tab()[j].getName());
                        }
                    }
                    return true;
                }
                return false;

            } else {

                if (args[0].equals("reset") && args.length == 1) {
                    g.reset();
                    commandSender.sendMessage("All the settings has been reset.");
                    return true;
                }

                if (args[0].equals("print")) {
                    commandSender.sendMessage("number of team : " + g.getTeam_tab_length());
                    for (int i = 0; i < g.getTeam_tab_length(); i++) {
                        commandSender.sendMessage("Color of team " + (i + 1) + " : " + g.getTeam_tab()[i].getColor());
                        for (int j = 0; j < g.getTeam_tab()[i].getPlayers_length(); j++) {
                            commandSender.sendMessage("Player " + (j + 1) + " : " + g.getTeam_tab()[i].getPlayer_tab()[j].getName());
                        }
                    }
                    return true;
                }

                return false;

            }
        }
        return false;
    }
}
