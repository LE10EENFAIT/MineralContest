package com.pl4giat.mineralcontest;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class GlobalData {

    /**Global variables**/
    private double[] spawn_location = {7.185, 78.0, -311.468};
    public double[] getSpawn_location(){ return spawn_location; }


    private Location cooldown_respawn_location = new Location(Bukkit.getServer().getWorld("World"), 215.044, 65.06250, -259.481);
    public Location getCooldown_respawn_location(){ return cooldown_respawn_location; }

    private RespawnRoom respawnRoom = new RespawnRoom();
    public RespawnRoom getRespawnRoom(){ return respawnRoom; }

    private World world = Bukkit.getWorld("World");
    public World getWorld() {return world; }


    HashMap<String, double[]> locations = new HashMap<String, double[]>();

    public void setLocations(double[] blue, double[] yellow, double[] red, double[] green){
        locations.put("blue", blue);
        locations.put("yellow", yellow);
        locations.put("red", red);
        locations.put("green", green);
    }

    public double[] getSpawn_location_by_color(String color){ return locations.get(color); }

    private double[] blue_team_spawn_location = {187.536, 76, -197.523};
    public double[] getBlue_team_spawn_location(){ return blue_team_spawn_location; }


    private double[] yellow_team_spawn_location = {186.807, 76, -271.521};
    public double[] getYellow_team_spawn_location() { return yellow_team_spawn_location; }


    private double[] red_team_spawn_location = {223.814, 76, -234.515};
    public double[] getRed_team_spawn_location(){ return red_team_spawn_location; }


    private double[] green_team_spawn_location = {149.468, 76, -235.218};
    public double[] getGreen_team_spawn_location(){ return green_team_spawn_location; }


    private ItemStack[] respawn_player_items = {new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.BOW, 1), new ItemStack(Material.ARROW, 64)};
    public ItemStack[] getRespawn_player_items(){ return respawn_player_items; }
    private ItemStack[] respawn_player_armor = {new ItemStack(Material.IRON_HELMET, 1), new ItemStack(Material.IRON_LEGGINGS, 1),
                                                new ItemStack(Material.IRON_BOOTS, 1), new ItemStack(Material.IRON_CHESTPLATE, 1)};
    public ItemStack[] getRespawn_player_armor(){ return respawn_player_armor; }

    public void equip_player(Player p){
        PlayerInventory inv = p.getInventory();
        for (ItemStack i : respawn_player_items) {
            inv.addItem(i);
        }
        inv.setHelmet(respawn_player_armor[0]);
        inv.setLeggings(respawn_player_armor[1]);
        inv.setBoots(respawn_player_armor[2]);
        inv.setChestplate(respawn_player_armor[3]);
    }


    /**Arena Events**/

    private double[] arena_location = {201.994, 71, -240.865};
    public Location getArenaLocation(){ return new Location(world, arena_location[0], arena_location[1], arena_location[2]); }

    private double[] arena_chest_location = {186.484, 68, -234.557};
    public double[] getArena_chest_location(){ return  arena_chest_location; }

    private ItemStack[] arena_chest_inventory = new ItemStack[4];
    public void init_arena_chest_inventory() {
        int[] random_amount = new int[4];
        Random r = new Random();
        random_amount[0] = r.nextInt(3);
        random_amount[1] = r.nextInt(5);
        random_amount[2] = r.nextInt(11);
        random_amount[3] = r.nextInt(18);

        arena_chest_inventory[0] = new ItemStack(Material.EMERALD, random_amount[0]);
        arena_chest_inventory[1] = new ItemStack(Material.DIAMOND, random_amount[1]);
        arena_chest_inventory[2] = new ItemStack(Material.GOLD_INGOT, random_amount[2]);
        arena_chest_inventory[3] = new ItemStack(Material.IRON_INGOT, random_amount[3]);
    }
    public ItemStack[] getArena_chest_inventory(){ return arena_chest_inventory; }

    private boolean isArenaChest = false;
    public void setIsArenaChest(boolean b){ isArenaChest = b; }
    public boolean getIsArenaChest(){ return isArenaChest; }

    public void add_arena_chest() {
        Location loc = new Location(world, arena_chest_location[0], arena_chest_location[1], arena_chest_location[2]);
        Block b = loc.getBlock();
        b.setType(Material.CHEST);
        Chest c = (Chest) b.getState();
        Inventory inv = c.getBlockInventory();
        init_arena_chest_inventory();
        ItemStack[] items = arena_chest_inventory;
        for (ItemStack i : items) {
            inv.addItem(i);
        }
        isArenaChest = true;
    }

    public void remove_arena_chest(){
        if(world != null) {
            Location loc = new Location(world, arena_chest_location[0], arena_chest_location[1], arena_chest_location[2]);
            Block b = loc.getBlock();
            Chest c = null;
            if (b.getState() instanceof Chest) {
                c = (Chest) b.getState();
            }
            if (c != null) {
                c.getBlockInventory().clear();
                b.setType(Material.AIR);
                isArenaChest = false;
            }
        }
    }

    public Chest getArenaChest(){
        Location loc = new Location(world, arena_chest_location[0], arena_chest_location[1], arena_chest_location[2]);
        Block b = loc.getBlock();
        return (Chest) b.getState();
    }

    private boolean spawn_arena_chest = false;
    public void setSpawn_arena_chest(boolean b){ spawn_arena_chest = b; }
    public boolean getSpawn_arena_chest(){ return spawn_arena_chest; }



    /***Dynamic data**/


    private boolean is_Started = false;
    public void setIs_Started(boolean b){
        is_Started = b;
    }
    public boolean getIs_Started(){
        return is_Started;
    }

    private Team[] team_tab = new Team[4];
    public void addTeam_tab(Team t){ team_tab[team_tab_length] = t; team_tab_length++;}
    public Team[] getTeam_tab(){
        return team_tab;
    }

    private int team_tab_length = 0;
    public int getTeam_tab_length() { return team_tab_length; }

    private boolean wait_before_start = false;
    public void setWait_before_start(boolean b){ wait_before_start = b; }
    public boolean getWait_before_start(){ return wait_before_start; }

    public String getTeamColorByPlayer(Player player){
        for(int i = 0; i < team_tab_length; i++){
            if(team_tab[i].isPlayer(player)){
                return team_tab[i].getColor();
            }
        }
        return "noteam";
    }

    public Player[] getPlayersByTeamColor(String color){
        for(Team t : team_tab){
            if(t.getColor().equals(color)){
                return t.getPlayer_tab();
            }
        }
        return null;
    }

    private long starting_time = 0;
    private long ending_time = 0;
    private long wait_time = 10;
    private int time_remaining = -1;
    private int modulo = 10;
    public void setModulo(int m){ modulo = m;}
    public int getModulo(){ return modulo; }
    public long getWait_time(){ return wait_time; }
    public void setTime(){
        starting_time = System.currentTimeMillis() / 1000;
        ending_time = starting_time + wait_time + 3600;
    }
    public long getElapsedTime(){
        return (System.currentTimeMillis() / 1000) - starting_time;
    }
    public long getEnding_time(){ return ending_time; }
    public long getStarting_time(){ return starting_time; }
    public long getTimeRemaining(){ return time_remaining; }
    public void setTimeRemaining(int time){ time_remaining = time; }

    private long[] time_spawn_arena_chest = new long[7];
    public void InitTimeSpawnArenaChest(){
        Random r = new Random();
        long time = ending_time - starting_time;
        for(int i = 0; i<4; i++){
            time_spawn_arena_chest[i] = r.nextInt((int) (time)) + wait_time;
        }

        for (int i = 4; i<time_spawn_arena_chest.length; i++){
            time_spawn_arena_chest[i] = time - (r.nextInt(480) + 120);
        }

        Arrays.sort(time_spawn_arena_chest);
    }
    public long[] getTime_spawn_arena_chest(){ return time_spawn_arena_chest; }

    private int nbArenaChest = 0;
    public void setNbArenaChest(int nbArenaChest) {
        if(nbArenaChest < time_spawn_arena_chest.length)
            this.nbArenaChest = nbArenaChest; }
    public int getNbArenaChest() {return nbArenaChest; }

    private Player[] players_event = new Player[16];
    private int players_event_length = 0;
    public void addPlayerEvent(Player p){
        players_event[players_event_length] = p;
        players_event_length++;
    }
    public Player[] getPlayers_event(){ return players_event; }
    public int getPlayers_event_length(){ return players_event_length; }
    public void sendMessagePlayers_event(String s){
        for(int i = 0; i < players_event_length; i++){
            players_event[i].sendMessage(s);
        }
        Bukkit.getConsoleSender().sendMessage(s);
    }
    public void sendMessagePlayers(Player[] players, String s){
        for(Player p : players){
            p.sendMessage(s);
        }
        Bukkit.getConsoleSender().sendMessage(s);
    }


    private double[] blue_chest_location = {180.462, 76, -197.417};
    public double[] getBlue_chest_location(){ return  blue_chest_location; }
    public Chest getBlueChest(){
        Chest c = null;
        Location loc = new Location(world, blue_chest_location[0], blue_chest_location[1], blue_chest_location[2]);
        Block b = loc.getBlock();
        if(b.getState() instanceof Chest)
            c = (Chest) b.getState();
        return c;
    }

    private double[] yellow_chest_location = {192.697, 76, -269.429};
    public double[] getYellow_chest_location(){ return  yellow_chest_location; }
    public Chest getYellowChest(){
        Chest c = null;
        Location loc = new Location(world, yellow_chest_location[0], yellow_chest_location[1], yellow_chest_location[2]);
        Block b = loc.getBlock();
        if(b.getState() instanceof Chest)
            c = (Chest) b.getState();
        return c;
    }

    private double[] red_chest_location = {222.572, 76, -228.300};
    public double[] getRed_chest_location(){ return  red_chest_location; }
    public Chest getRedChest(){
        Chest c = null;
        Location loc = new Location(world, red_chest_location[0], red_chest_location[1], red_chest_location[2]);
        Block b = loc.getBlock();
        if(b.getState() instanceof Chest)
            c = (Chest) b.getState();
        return c;
    }

    private double[] green_chest_location = {151.300, 76, -240.700};
    public double[] getGreen_chest_location(){ return  green_chest_location; }
    public Chest getGreenChest(){
        Chest c = null;
        Location loc = new Location(world, green_chest_location[0], green_chest_location[1], green_chest_location[2]);
        Block b = loc.getBlock();
        if(b.getState() instanceof Chest)
            c = (Chest) b.getState();
        return c;
    }

    public int getChestPoints(Chest c, String color){
        int indice =0;
        int points = 0;
        Inventory inv = c.getBlockInventory();
        switch (color){
            case "blue":
                indice = 0;
                break;
            case "yellow":
                indice = 1;
                break;
            case "red":
                indice = 2;
                break;
            case "green":
                indice = 3;
                break;
        }
        ItemStack[] items = inv.getContents();
        for(ItemStack item : items) {
            if (item != null) {
                if(item.getType().equals(Material.EMERALD)){
                    for(int i = 0; i<item.getAmount(); i++)
                        points += 1000;
                }
                if(item.getType().equals(Material.DIAMOND)) {
                    for (int i = 0; i < item.getAmount(); i++)
                        points += 500;
                }
                if (item.getType().equals(Material.GOLD_INGOT)) {
                    for (int i = 0; i < item.getAmount(); i++)
                        points += 200;
                }
                if (item.getType().equals(Material.IRON_INGOT)) {
                    for (int i = 0; i < item.getAmount(); i++)
                        points += 10;
                }
                if(item.getType().equals(Material.DRAGON_EGG)) {
                    for (int i = 0; i < item.getAmount(); i++)
                        points += 10000;
                }
            }
        }
        return points;
    }

    public void clearChest(){
        getBlueChest().getBlockInventory().clear();
        getYellowChest().getBlockInventory().clear();
        getRedChest().getBlockInventory().clear();
        getGreenChest().getBlockInventory().clear();
    }



    /**Reset**/
    public void reset() {
        team_tab_length = 0;
        is_Started = false;
        remove_arena_chest();
        players_event_length = 0;
        spawn_arena_chest = false;
        if(world != null)
            clearChest();
    }

}


