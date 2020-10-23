package me.fluffy.fluffessentials.helpers.userstuff;

import me.fluffy.fluffessentials.helpers.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UserData {
    private final Player player;
    private Location homeLocation;
    private TeleportRequest currentRequest = null;
    private ArrayList<TeleportRequest> incomingRequests = new ArrayList<>();
    private int balance;
    private boolean hasReadAnnouncement;

    public UserData(Player p,int b,Location homeLocation,boolean hr){
        this.player = p;
        this.homeLocation = homeLocation;
        this.balance = b;
        this.hasReadAnnouncement = hr;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean makeTeleportRequest(UserData target){
        Bukkit.getLogger().info("tp:" + this.currentRequest);
        if(this.currentRequest == null){
            this.currentRequest = new TeleportRequest(this.getPlayer(),target.getPlayer());
            target.getRequests().add(this.currentRequest);
            Bukkit.getLogger().info("REQ:" + target.getRequests());
            return true;
        } else {
            return false;
        }
    }

    public TeleportRequest getCurrentRequest(){
        return this.currentRequest;
    }

    public void endRequest(){
        this.currentRequest = null;
    }

    public ArrayList<TeleportRequest> getRequests(){
        return this.incomingRequests;
    }

    public Location getHomeLocation(){
        return this.homeLocation;
    }

    public void setHomeLocation(Location l){
        this.homeLocation = l;
    }

    public int getBalance() {
        return balance;
    }

    public boolean changeBalance(int amountToChange){
        if(this.balance + amountToChange < 0){
            return false;
        } else {
            this.balance += amountToChange;
            return true;
        }
    }

    public boolean hasReadAnnouncement(){
        return this.hasReadAnnouncement;
    }

    public void readAnnouncement(){
        this.hasReadAnnouncement = true;
    }

    public void nonRead(){
        this.hasReadAnnouncement = false;
    }
}
