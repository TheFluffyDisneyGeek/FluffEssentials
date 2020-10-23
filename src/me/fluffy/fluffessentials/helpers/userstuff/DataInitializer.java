package me.fluffy.fluffessentials.helpers.userstuff;


import me.fluffy.fluffessentials.MainLoader;
import me.fluffy.fluffessentials.helpers.Welcome;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class DataInitializer implements Listener {
    Map<Player,UserData> loadedUserData = new HashMap<>();
    private final MainLoader plugin;
    public DataInitializer(MainLoader m){
        this.plugin = m;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if (!loadedUserData.containsKey(event.getPlayer())) {
            String pUuid = event.getPlayer().getUniqueId().toString();
            if(plugin.getDataFile().getString(pUuid,"error").equals("error")){
                Bukkit.getLogger().info("Player does not have data! Creating data now...");
                saveData(event.getPlayer(),0,"noHome",false);
                loadedUserData.put(event.getPlayer(),new UserData(event.getPlayer(),0,null,false));
            } else {
                String StringLoc = plugin.getDataFile().getString(pUuid + ".home-location");
                int balance = plugin.getDataFile().getInt(pUuid + ".balance");
                Location homeLocation;
                if(StringLoc.equals("noHome")){
                    homeLocation = null;
                } else {
                    homeLocation = getLiteLocationFromString(StringLoc);
                }
                boolean hr = plugin.getDataFile().getBoolean(pUuid + ".has-read");
                loadedUserData.put(event.getPlayer(),new UserData(event.getPlayer(),balance,homeLocation,hr));
            }
            Welcome.welcomePlayer(event.getPlayer(),this);

        }
    }
    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        //this gives error
        //it's because player doesn't have data, then leaves. need to fix the saveConfig. may have fixed it, test some more.
        if(loadedUserData.containsKey(event.getPlayer())) {
            saveData(event.getPlayer(), getPlayerData(event.getPlayer()).getBalance(), locationToString(getPlayerData(event.getPlayer()).getHomeLocation()),getPlayerData(event.getPlayer()).hasReadAnnouncement());
            loadedUserData.remove(event.getPlayer());
        }
    }

    private void saveData(Player p, int balance, String homeLocation, boolean hr){
        String pUuid = p.getUniqueId().toString();
        plugin.getDataFile().set(pUuid + ".home-location",homeLocation);
        plugin.getDataFile().set(pUuid + ".balance",balance);
        plugin.getDataFile().set(pUuid + ".has-read",hr);
        plugin.saveCustomConfig();
    }

    public UserData getPlayerData(Player p){
        return loadedUserData.get(p);
    }

    private String locationToString(Location loc) {
        if(loc == null){
            return "noHome";
        } else {
            return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
        }
    }

    private Location getLiteLocationFromString(String s) {
        if (s == null || s.trim().equals("")) {
            Bukkit.getLogger().warning("Problem loading location!");
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }


}
