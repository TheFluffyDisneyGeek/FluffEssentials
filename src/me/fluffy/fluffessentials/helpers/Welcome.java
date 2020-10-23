package me.fluffy.fluffessentials.helpers;

import me.fluffy.fluffessentials.commands.TimeCommands;
import me.fluffy.fluffessentials.helpers.userstuff.DataInitializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Welcome {
    public static void welcomePlayer(Player p, DataInitializer d){
        p.sendMessage(ChatColor.GOLD + "Welcome! There are " + ChatColor.RED + (Bukkit.getOnlinePlayers().size() - 1) + ChatColor.GOLD + " other players online!");
        p.sendMessage(ChatColor.GOLD + "The time is: " + ChatColor.GREEN + TimeCommands.getTime());
        if(!d.getPlayerData(p).hasReadAnnouncement()){
            p.sendMessage(ChatColor.GOLD + "New announcement! /readannouncement to read it!");
        }
    }
}
