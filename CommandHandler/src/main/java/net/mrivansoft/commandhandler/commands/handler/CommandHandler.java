package net.mrivansoft.commandhandler.commands.handler;

import net.mrivansoft.commandhandler.Main;
import net.mrivansoft.commandhandler.commands.ExampleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class CommandHandler implements CommandExecutor {
    private Main plugin;
    private HashMap<String, SubCommand> commands = new HashMap<>();
    private ArrayList<SubCommand> commandExecutors = new ArrayList<>();

    public CommandHandler(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("examplecmd").setExecutor(this);
        addCommand(new ExampleCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage(plugin.color("&aExamplePlugin version " + plugin.getDescription().getVersion()));
            sender.sendMessage(plugin.color("&bUse /bank help for help!"));
            return true;
        }else if((args.length == 1) && (args[0].equalsIgnoreCase("help"))){
            sender.sendMessage(plugin.color("&aCommands: "));
            commandExecutors.forEach(ex -> {
                String name = ex.getName();
                sender.sendMessage(plugin.color("&e/examplecmd " + name + "&a - " + getDescription(ex) + "\n &9Permission: " + getPermission(ex)));
            });
            return true;
        }else{
            if(commands.containsKey(args[0])){
                SubCommand subCommand = commands.get(args[0]);
                if((!subCommand.getPermission().equalsIgnoreCase("")) && (!sender.hasPermission(subCommand.getPermission()))){
                    sender.sendMessage(plugin.color("&cYou dont have permission to perform this command!"));
                    return true;
                }else{
                    subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
                    return true;
                }
            }else{
                sender.sendMessage(plugin.color("&cNo matches found by \"/examplecmd " + args[0] + "\"."));
            }
        }
        return true;
    }

    private void addCommand(SubCommand executor){
        commands.put(executor.getName(), executor);
        commandExecutors.add(executor);
    }

    private String getDescription(SubCommand executor){
        String desc;
        if(isNullOrEmpty(executor.getDescription())){
            desc = "No description provided";
        }else{
            desc = executor.getDescription();
        }
        return desc;
    }

    private String getPermission(SubCommand executor){
        String perm;
        if(isNullOrEmpty(executor.getPermission())){
            perm = "No permission";
        }else{
            perm = executor.getPermission();
        }
        return perm;
    }

    private boolean isNullOrEmpty(String toTest){
        return toTest == null || toTest.length() == 0;
    }
}