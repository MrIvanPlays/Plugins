package net.mrivansoft.commandhandler.commands;

import net.mrivansoft.commandhandler.commands.handler.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class ExampleCommand extends SubCommand {
    private JavaPlugin plugin;

    public ExampleCommand(JavaPlugin plugin) {
        super("example", "An example command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage("Hello world!");
            return true;
        }else if(args.length == 1){
            sender.sendMessage("You've found my secret message! Deleting in 5 secs...");
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for(int i = 0; i < 200; i++){
                    sender.sendMessage("");
                }
            }, 100);
            return true;
        }
        return true;
    }
}