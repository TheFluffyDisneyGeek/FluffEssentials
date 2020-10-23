package me.fluffy.fluffessentials.commands;

import me.fluffy.fluffessentials.MainLoader;
import me.fluffy.fluffessentials.helpers.TeleportRequest;
import me.fluffy.fluffessentials.helpers.userstuff.DataInitializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class TeleportCommands implements CommandExecutor {
    private final MainLoader plugin;
    private final DataInitializer dataInit;
    public TeleportCommands(MainLoader plugin) {
        this.plugin = plugin;
        this.dataInit = plugin.getDataInit();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (label.equalsIgnoreCase("tpa")) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){ //all this is input validation
                    p.sendMessage(ChatColor.RED + "Invalid Player!");
                    return false;
                }
                if(target == p){
                    p.sendMessage(ChatColor.RED + "Why would you need to send a request to yourself?");
                    return false;
                }
                if(!dataInit.getPlayerData(p).makeTeleportRequest(dataInit.getPlayerData(target))){
                    p.sendMessage(ChatColor.RED + "You can only send one request at a time!");
                    return false;
                }
                target.sendMessage(ChatColor.GREEN + target.getName() + ChatColor.GOLD + " has requested to teleport to you! \n" + ChatColor.GREEN + "/tpaccept " + ChatColor.GOLD + "to accept.");
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if(dataInit.getPlayerData(p).getCurrentRequest() != null){
                            if(dataInit.getPlayerData(p).getCurrentRequest().getTarget().equals(target)){
                                p.sendMessage(ChatColor.RED + "Your request has expired!");
                                int requestIndex = 5000;
                                ArrayList<TeleportRequest> requests = dataInit.getPlayerData(dataInit.getPlayerData(p).getCurrentRequest().getTarget()).getRequests();
                                for(TeleportRequest t:requests){
                                    if(t.getSender() == p){
                                        requestIndex = requests.indexOf(t);
                                        dataInit.getPlayerData(t.getSender()).endRequest();
                                        break;
                                        //this avoids the ConcurrentModificationException error
                                    }
                                }
                                requests.remove(requestIndex);
                            }
                        }
                    }
                },1200);
                return true;

            }
            if (label.equalsIgnoreCase("tpaccept")){
                ArrayList<TeleportRequest> requests = dataInit.getPlayerData(p).getRequests();
                if(requests.isEmpty()){
                    p.sendMessage(ChatColor.RED + "You have no incoming requests!");
                    return false;
                }
                TeleportRequest t = requests.get(0);
                t.getSender().teleport(p);
                t.getSender().sendMessage(ChatColor.GREEN + "Your request has been accepted!");
                requests.remove(t);
                dataInit.getPlayerData(t.getSender()).endRequest();
            }
            if (label.equalsIgnoreCase("tpdeny")) {
                ArrayList<TeleportRequest> requests = dataInit.getPlayerData(p).getRequests();
                if(requests.isEmpty()){
                    p.sendMessage(ChatColor.RED + "You have no incoming requests!");
                    return false;
                }
                TeleportRequest t = requests.get(0);
                t.getSender().sendMessage(ChatColor.RED + "Your request has been denied!");
                requests.remove(t);
                dataInit.getPlayerData(t.getSender()).endRequest();
            }
        }
        return false;
    }



}
