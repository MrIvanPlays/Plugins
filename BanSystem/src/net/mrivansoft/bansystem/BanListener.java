package net.mrivansoft.bansystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * Created by MrIvanPlays on 2018
 * © All rights reserved
 */
public class BanListener implements Listener {
    private Main plugin;

    public BanListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void preventBannedPlayerJoining(AsyncPlayerPreLoginEvent event){
        UUID uuid = event.getUniqueId();
        if(plugin.getBans().getBoolean(uuid + ".isBanned")){
            final String reason = plugin.getBans().getString(uuid + ".reason");
            final String bannedBy = plugin.getBans().getString(uuid + ".banner");
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, plugin.color("You are banned! \n Reason: " + reason + "\n Banned by: " + bannedBy));
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()){
            plugin.getBans().set(player.getUniqueId() + ".isBanned", false);
            plugin.saveBans();
        }
    }
}