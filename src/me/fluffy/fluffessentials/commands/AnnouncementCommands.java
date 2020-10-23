package me.fluffy.fluffessentials.commands;

import me.fluffy.fluffessentials.MainLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AnnouncementCommands implements CommandExecutor {

    private final MainLoader plugin;

    public AnnouncementCommands(MainLoader m){
        this.plugin = m;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(label.equalsIgnoreCase("announce")){
                if (p.getName().equalsIgnoreCase("TheFluffyKing")) {
                    String delim = " ";
                    String res = String.join(delim, args);
                    plugin.getDataFile().set("current-announcement",res);
                    for(OfflinePlayer op: Bukkit.getOfflinePlayers()){
                        if(!plugin.getDataFile().getString(String.valueOf(op.getUniqueId()),"error").equals("error")){
                            plugin.getDataFile().set(op.getUniqueId() + ".has-read",false);
                        }
                    }
                    for(Player onlinePlayer: Bukkit.getOnlinePlayers()){
                        onlinePlayer.sendMessage(ChatColor.GOLD + "New announcement! /readannouncement to read it!");
                        plugin.getDataInit().getPlayerData(onlinePlayer).nonRead();
                        if(!plugin.getDataFile().getString(String.valueOf(onlinePlayer.getUniqueId()),"error").equals("error")){
                            plugin.getDataFile().set(onlinePlayer.getUniqueId() + ".has-read",false);
                        }
                    }
                    p.sendMessage(ChatColor.GREEN + "Announcement created!");
                    plugin.saveCustomConfig();
                    return true;
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You don't have permission to do that!");
                    return false;
                }
            }
            if(label.equalsIgnoreCase("readannouncement")){
                if(plugin.getDataFile().getString("current-announcement") == null){
                    p.sendMessage("There is no current announcement!");
                    return false;
                } else {
                    p.sendMessage(ChatColor.GOLD + plugin.getDataFile().getString("current-announcement"));
                    plugin.getDataInit().getPlayerData(p).readAnnouncement();
                    plugin.saveCustomConfig();
                }
            }
        } else {
            String delim = " ";
            String res = String.join(delim, args);
            plugin.getDataFile().set("current-announcement",res);
            for(OfflinePlayer op: Bukkit.getOfflinePlayers()){
                if(!plugin.getDataFile().getString(String.valueOf(op.getUniqueId()),"error").equals("error")){
                    plugin.getDataFile().set(op.getUniqueId() + ".has-read",false);
                }
            }
            for(Player onlinePlayer: Bukkit.getOnlinePlayers()){
                onlinePlayer.sendMessage(ChatColor.GOLD + "New announcement! /readannouncement to read it!");
                plugin.getDataInit().getPlayerData(onlinePlayer).nonRead();
                if(!plugin.getDataFile().getString(String.valueOf(onlinePlayer.getUniqueId()),"error").equals("error")){
                    plugin.getDataFile().set(onlinePlayer.getUniqueId() + ".has-read",false);
                }
            }
            commandSender.sendMessage(ChatColor.GREEN + "Announcement created!");
            plugin.saveCustomConfig();
            return true;
        }
        return false;
    }
}
