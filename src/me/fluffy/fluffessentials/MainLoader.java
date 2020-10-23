package me.fluffy.fluffessentials;

import me.fluffy.fluffessentials.commands.*;
import me.fluffy.fluffessentials.helpers.userstuff.DataInitializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MainLoader extends JavaPlugin {
    private DataInitializer dataInit;
    private File customConfigFile;
    private YamlConfiguration dataFile;


    @Override
    public void onEnable(){
        createDataFile();
        this.dataInit = new DataInitializer(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.dataInit,this);
        Bukkit.getLogger().info("FluffEssentials was successfully loaded!");
        TeleportCommands tpCmd = new TeleportCommands(this);
        getCommand("tpa").setExecutor(tpCmd);
        getCommand("tpaccept").setExecutor(tpCmd);
        getCommand("tpdeny").setExecutor(tpCmd);
        getCommand("clock").setExecutor(new TimeCommands());
        HomeCommands hcmd = new HomeCommands(this);
        getCommand("sethome").setExecutor(hcmd);
        getCommand("home").setExecutor(hcmd);
        MoneyCommands mcommand = new MoneyCommands(this);
        getCommand("pay").setExecutor(mcommand);
        getCommand("balance").setExecutor(mcommand);
        getCommand("deposit").setExecutor(mcommand);
        AnnouncementCommands ac = new AnnouncementCommands(this);
        getCommand("readannouncement").setExecutor(ac);
        getCommand("announce").setExecutor(ac);
    }

    public FileConfiguration getDataFile() {
        return this.dataFile;
    }

    public void saveCustomConfig() {
        try {
            this.dataFile.save(this.customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDataFile() {
        this.customConfigFile = new File(getDataFolder(), "data.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        dataFile = new YamlConfiguration();
        try {
            dataFile.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public DataInitializer getDataInit(){
        return this.dataInit;
    }

}
