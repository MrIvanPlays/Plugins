package net.mrivansoft.bansystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class BanCommand implements CommandExecutor {
    private Main plugin;

    public BanCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("ban")){
            if(sender.hasPermission("bansystem.ban")){
                if(args.length == 0){
                    sender.sendMessage(ChatColor.RED + "Usage: /ban <player> <reason>");
                    return true;
                }else if(args.length == 1){
                    sender.sendMessage(ChatColor.RED + "Usage: /ban <player> <reason>");
                    return true;
                }else{
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if(target == null){
                        sender.sendMessage(ChatColor.RED + "ERROR: Cannot find player " + args[0]);
                        return true;
                    }
                    String reason = "";
                    for(int i = 1; i < args.length; i++){
                        reason += " " + args[i];
                    }
                    plugin.getBans().set(target.getUniqueId() + ".isBanned", true);
                    plugin.getBans().set(target.getUniqueId() + ".reason", reason);
                    plugin.getBans().set(target.getUniqueId() + ".banner", sender.getName());
                    plugin.saveBans();
                    target.kickPlayer(plugin.color("You are banned! \n Reason: " + reason + "\n Banned by: " + sender.getName()));
                    plugin.getServer().broadcastMessage("Player " + args[0] + " was banned from the server with reason '" + reason + "'.");
                    return true;
                }
            }
        }
        if(command.getName().equalsIgnoreCase("unban")){
            if(sender.hasPermission("bansystem.unban")){
                if(args.length == 0){
                    sender.sendMessage(ChatColor.RED + "Usage: /unban <player>");
                    return true;
                }else if(args.length == 1){
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if(!plugin.getConfig().getBoolean(target.getUniqueId() + ".isBanned")){
                        sender.sendMessage(ChatColor.RED + "That player is not banned!");
                        return true;
                    }
                    plugin.getBans().set(target.getUniqueId() + ".isBanned", false);
                    plugin.getBans().set(target.getUniqueId() + ".reason", "");
                    plugin.getBans().set(target.getUniqueId() + ".banner", "");
                    plugin.saveBans();
                    plugin.getServer().broadcastMessage("Player " + args[0] + "was unbanned from the server.");
                    return true;
                }
            }
        }
        return true;
    }
}