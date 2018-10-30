package net.mrivansoft.bansystem;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class Main extends JavaPlugin {
    public String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private File banFile;
    private FileConfiguration banCfg;

    public void onEnable(){
        generateBanFile();
        getServer().getPluginManager().registerEvents(new BanListener(this), this);
        getCommand("ban").setExecutor(new BanCommand(this));
        getCommand("ban").setPermissionMessage(ChatColor.RED + "Lol no...");
        getCommand("unban").setExecutor(new BanCommand(this));
        getCommand("unban").setPermissionMessage(ChatColor.RED + "Lol no...");
        getLogger().info("Plugin enabled!");
    }

    private void generateBanFile(){
        banFile = new File(getDataFolder(), File.separator + "bans.yml");
        if(!banFile.exists()){
            try{
                banFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        banCfg = YamlConfiguration.loadConfiguration(banFile);
        banCfg.options().header("DONT CHANGE IT OR ILL BAN YOU!!!!!");
    }

    public void saveBans(){
        try{
            banCfg.save(banFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getBans(){
        return banCfg;
    }
}