package net.mrivansoft.commandhandler;

import net.mrivansoft.commandhandler.commands.handler.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public void onEnable() {
        new CommandHandler(this);
        getLogger().info("Hello, world!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye, world!");
    }
}