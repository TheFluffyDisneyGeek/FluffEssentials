package me.fluffy.fluffessentials.commands;

import me.fluffy.fluffessentials.MainLoader;
import me.fluffy.fluffessentials.helpers.userstuff.DataInitializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoneyCommands implements CommandExecutor {

    private final DataInitializer dataInit;

    public MoneyCommands(MainLoader m){
        this.dataInit = m.getDataInit();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("pay")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.DARK_RED + "That player does not exist or is not online!");
                    return false;
                }
                int amount;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    p.sendMessage(ChatColor.DARK_RED + "You either didn't provide a number, or your input was not a nice number!");
                    return false;
                }
                if (amount < 0) {
                    p.sendMessage(ChatColor.DARK_RED + "I see what you did there. No.");
                    return false;
                }
                if (dataInit.getPlayerData(p).changeBalance(-amount)) {
                    dataInit.getPlayerData(target).changeBalance(amount);
                    p.sendMessage(ChatColor.GREEN + String.format("Successfully transferred %s diamonds to %s's account!", amount, target.getName()));
                    return true;
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have enough money to do that!");
                    return false;
                }
            } else {
                commandSender.sendMessage("no cheating. this is definitely not cuz i'm lazy");
                return true;
            }
        }

        if(label.equalsIgnoreCase("balance")){
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                int balance = dataInit.getPlayerData(p).getBalance();
                p.sendMessage(ChatColor.GOLD + "You have " + ChatColor.AQUA + balance + ChatColor.GOLD + " diamonds in your account!");
                return true;
            }
            return false;
        }
        if(label.equalsIgnoreCase("deposit")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                int amount;
                try {
                    amount = Integer.parseInt(args[0]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    p.sendMessage(ChatColor.DARK_RED + "You either didn't provide a number, or your input was not a nice number!");
                    return false;
                }
                if (amount < 0) {
                    p.sendMessage(ChatColor.DARK_RED + "Negative diamonds do not exist. Please try again.");
                    return false;
                }
                ItemStack i = new ItemStack(Material.DIAMOND, amount);
                if(p.getInventory().getItemInMainHand().isSimilar(i)) {
                    if (p.getInventory().getItemInMainHand().getAmount() >= i.getAmount()) {
                        p.sendMessage(ChatColor.GREEN +"Successfully deposited " + ChatColor.AQUA + amount + ChatColor.GREEN + " diamonds!");
                        p.getInventory().removeItem(i);
                        dataInit.getPlayerData(p).changeBalance(amount);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
