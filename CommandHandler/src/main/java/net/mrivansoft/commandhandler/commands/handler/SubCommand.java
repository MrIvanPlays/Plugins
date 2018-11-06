package net.mrivansoft.commandhandler.commands.handler;

import org.bukkit.command.CommandSender;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public abstract class SubCommand {
    private String name;
    private String permission;
    private String description;

    public SubCommand(String name, String description){
        this(name, "", description);
    }

    public SubCommand(String name, String permission, String description){
        this.name = name;
        this.permission = permission;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription(){
        return description;
    }

    public abstract boolean execute(CommandSender sender, String[] args);
}