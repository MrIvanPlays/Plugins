package net.mrivansoft.chroniumlobby;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ChroniumLobby extends Plugin {

    public String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    private File config;
    private Configuration configuration;

    @Override
    public void onEnable() {
        loadConfig();
        getProxy().getPluginManager().registerCommand(this, new TheCommand(this));
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    private void loadConfig(){
        if(!getDataFolder().exists()) getDataFolder().mkdirs();
        config = new File(getDataFolder(), File.separator + "config.yml");
        if(!config.exists()){
            try(InputStream in = getResourceAsStream("config.yml")){
                Files.copy(in, config.toPath());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        reloadConfig();
    }

    public void reloadConfig(){
        try{
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig(){
        return configuration;
    }
}