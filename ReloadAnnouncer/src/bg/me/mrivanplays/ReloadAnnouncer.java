package bg.me.mrivanplays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ReloadAnnouncer extends JavaPlugin{

    private String format(String msg) {return ChatColor.translateAlternateColorCodes('&', msg);}
    private String prefix = format("&dReloadAnnouncer &7>> ");

    @Override
    public void onEnable() {
        registerConfig();
        getCommand("sreload").setExecutor(this);
        getCommand("rlan").setExecutor(this);
        getCommand("rlan").setPermissionMessage(format(getConfig().getString("noperm-message")));
        getCommand("sreload").setPermissionMessage(format(getConfig().getString("noperm-message")));
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Plugin enabled! v" + getDescription().getVersion());
    }
    @Override
    public void onDisable() { Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Plugin disabled!"); }
    private void registerConfig() {
        File f = new File(getDataFolder(), File.separator + "config.yml");
        if(!f.exists()) {
            saveResource("config.yml", false);
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Creating default config");
        }else{
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Configuration found, loading it");
        }
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sreload")) {
            if(sender.isOp()) {
                Bukkit.broadcastMessage(format(getConfig().getString("broadcast-message")));
                if(getConfig().getBoolean("titlemessage.enable")) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        String title = format(getConfig().getString("titlemessage.title"));
                        String subTitle = format(getConfig().getString("titlemessage.subtitle"));
                        online.sendTitle(title, subTitle);
                    }
                }
                Bukkit.reload();
                if(getConfig().getBoolean("reload-complete-messages.enable")) {
                    Bukkit.broadcastMessage(format(getConfig().getString("reload-complete-messages.chat-message")));
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        String title = format(getConfig().getString("reload-complete-messages.title-message"));
                        online.sendTitle(title, "");
                        return true;
                    }
                    return true;
                }
                return true;
            }
        }
        if(cmd.getName().equalsIgnoreCase("rlan")) {
            if(sender.hasPermission("reloadannouncer.basic")) {
                if(args.length == 0) {
                    sender.sendMessage(format("&aYou're running v" + getDescription().getVersion() + " of ReloadAnnouncer plugin"));
                    sender.sendMessage(format("&aDeveloper: MrIvanPlays"));
                    sender.sendMessage(format("&aCommands:"));
                    sender.sendMessage(format("&e/sreload &a- Announces reloading and reloads the server"));
                    sender.sendMessage(format("&eNOTE: The command /sreload can be run only from OP players!"));
                    sender.sendMessage(format("&cWARNING: If you do only /reload there will be no announce!"));
                    sender.sendMessage(format("&e/rlan reload &a- Reloads the plugin"));
                    return true;
                }
            }
            if(sender.hasPermission("reloadannouncer.reload")) {
                if((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                    reloadConfig();
                    sender.sendMessage(format("&2Plugin reloaded successfully!"));
                    return true;
                }
            }
        }
        return true;
    }
}