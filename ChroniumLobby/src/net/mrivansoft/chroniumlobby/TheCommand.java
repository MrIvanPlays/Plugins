package net.mrivansoft.chroniumlobby;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class TheCommand extends Command {
    private ChroniumLobby plugin;

    public TheCommand(ChroniumLobby plugin) {
        super("hub", null, new String[] { "lobby" });
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(!player.getServer().getInfo().getName().equalsIgnoreCase("lobby")){
                ServerInfo hub = ProxyServer.getInstance().getServerInfo("lobby");
                player.connect(hub);
                player.sendMessage(new TextComponent(plugin.color(plugin.getConfig().getString("success-connect"))));
            }else{
                player.sendMessage(new TextComponent(plugin.color(plugin.getConfig().getString("already-connected"))));
            }
        }else{
            sender.sendMessage(new TextComponent(plugin.color(plugin.getConfig().getString("no-console"))));
        }
    }
}