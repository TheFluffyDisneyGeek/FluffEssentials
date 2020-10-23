package me.fluffy.fluffessentials.helpers;

import me.fluffy.fluffessentials.MainLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportRequest {
    private Player sender;
    private Player target;
    public TeleportRequest(Player sender, Player target){
        this.sender = sender;
        this.target = target;
    }

    public Player getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }
}
