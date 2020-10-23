package me.fluffy.fluffessentials.commands;

import me.fluffy.fluffessentials.MainLoader;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommands implements CommandExecutor {

    private final MainLoader plugin;

    public HomeCommands(MainLoader m){
        this.plugin = m;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(label.equalsIgnoreCase("sethome")){
                Location loc = p.getLocation();
                plugin.getDataInit().getPlayerData(p).setHomeLocation(loc);
                p.sendMessage(ChatColor.GREEN + String.format("Home set to: %s,%s,%s",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
                return true;
            }
            if(label.equalsIgnoreCase("home")){
                Location loc = plugin.getDataInit().getPlayerData(p).getHomeLocation();
                if(loc == null){
                    p.sendMessage(ChatColor.DARK_RED + "You have not set a home!");
                    return false;
                }
                p.teleport(loc);
                p.sendMessage(ChatColor.GREEN + "Teleported!");
                return true;
            }
        }
        commandSender.sendMessage("Homes can only be modified and used by players!");
        return false;
    }
}
