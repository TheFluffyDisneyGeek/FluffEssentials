package me.fluffy.fluffessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class TimeCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("clock")){
            commandSender.sendMessage(ChatColor.GOLD + "The time is: " + ChatColor.GREEN + getTime());
            commandSender.sendMessage(ChatColor.GOLD + "The date is: " + ChatColor.GREEN + getDate());
        }
        return false;
    }
    public static String getTime(){
        double serverTime = Bukkit.getWorlds().get(0).getTime();
        int hours = ((int)(serverTime / 1000) + 6) % 24;
        int minutes = (int) (((serverTime / 1000) % 1) * 60);
        return formatTime(hours,2)+":"+ formatTime(minutes,2);
    }
    public static String getDate(){
        long dayAmount = Bukkit.getWorlds().get(0).getFullTime() / 24000;
        long day;
        long dayOfYear = dayAmount % 365;
        String month;
        String season;
        if(dayOfYear >= 0 && dayOfYear <= 29){
            month ="January";
            day = dayOfYear + 1;
            season = ChatColor.WHITE + "Winter";

        } else if(dayOfYear >= 30 && dayOfYear <= 59){
            month ="Feburary";
            day = dayOfYear - 29;
            season = ChatColor.WHITE + "Winter";

        } else if(dayOfYear >= 60 && dayOfYear <= 90){
            month ="March";
            day = dayOfYear - 59;
            season = ChatColor.LIGHT_PURPLE + "Spring";

        } else if(dayOfYear >= 91 && dayOfYear <= 120){
            month ="April";
            day = dayOfYear - 90;
            season = ChatColor.LIGHT_PURPLE + "Spring";

        } else if(dayOfYear >= 121 && dayOfYear <= 151){
            month ="May";
            day = dayOfYear - 120;
            season = ChatColor.LIGHT_PURPLE + "Spring";

        } else if(dayOfYear >= 152 && dayOfYear <= 181){
            month ="June";
            day = dayOfYear - 151;
            season = ChatColor.YELLOW + "Summer";

        } else if(dayOfYear >= 182 && dayOfYear <= 212){
            month ="July";
            day = dayOfYear - 181;
            season = ChatColor.YELLOW + "Summer";

        } else if(dayOfYear >= 213 && dayOfYear <= 242){
            month ="August";
            day = dayOfYear - 212;
            season = ChatColor.YELLOW + "Summer";

        } else if(dayOfYear >= 243 && dayOfYear <= 273){
            month ="September";
            day = dayOfYear - 242;
            season = ChatColor.DARK_GREEN + "Fall";

        } else if(dayOfYear >= 274 && dayOfYear <= 303){
            month ="October";
            day = dayOfYear - 273;
            season = ChatColor.DARK_GREEN + "Fall";

        } else if(dayOfYear >= 304 && dayOfYear <= 334){
            month ="November";
            day = dayOfYear - 303;
            season = ChatColor.DARK_GREEN + "Fall";

        } else if(dayOfYear >= 335){
            month ="December";
            day = dayOfYear - 334;
            season = ChatColor.WHITE + "Winter";

        } else {
            Bukkit.getLogger().info("dayOfYear:" + dayOfYear);
            month = null;
            day = 0;
            season = null;

        }
        return month + " " + day + ", " + (dayAmount/365) + ", " + season;
    }

    public static String formatTime(int n, int digits) {
        StringBuilder s = new StringBuilder(Integer.toString(n));
        while (s.length() < digits) {
            s.insert(0, '0');
        }
        return s.toString();
    }
}
