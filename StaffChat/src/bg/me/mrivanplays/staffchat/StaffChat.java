package bg.me.mrivanplays.staffchat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffChat extends JavaPlugin implements CommandExecutor, Listener {
	
	public String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&4StaffChat&7]");
	public String noPermission = getConfig().getString("noperm-message").replaceAll("&", "§");
	private String key = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";
	public static ArrayList<Player> lnsc = new ArrayList<Player>();
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Plugin enabled");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Version: " + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Developer: MrIvanPlays");
		Bukkit.getPluginManager().registerEvents(this, this);
		getCommand("sc").setExecutor(this);
		getCommand("sc").setPermission("staffchat.basic");
		getCommand("sc").setPermissionMessage(noPermission);
		registerConfig();
		updateChecker();
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Plugin disabled");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Version: " + getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Developer: MrIvanPlays");
	}
	private void registerConfig() {
		File f = new File(getDataFolder() + File.separator + "config.yml");
		if(!f.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Creating default config");
		} else {
			reloadConfig();
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "config.yml found, loading config");
		}
		saveDefaultConfig();
	}
	private void updateChecker() {
		if(getConfig().getString("update-check").equals("true")) {
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "Checking for updates...");
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=54896").openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.getOutputStream().write((key + 54896).getBytes("UTF-8"));
				String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				if(version.equals(this.getDescription().getVersion())) {
					Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "No updates available. Plugin is up to date!");
				}else {
					Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + "Update available for StaffChat!");
					Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + "Download it from: https://www.spigotmc.org/resources/staffchat.54896/");
				}
			}catch(IOException e) {
				Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "ERROR: Could not connect with Spigot");
			}
		}else {
			Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + "UpdateChecker set to false, no update-checking will be proceed");
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sc")) {
			if(args.length == 0) {
				if(sender.hasPermission("staffchat.basic")) {
					sender.sendMessage(ChatColor.RED + "Too small arguments. For help use: /sc help");
					return true;
				}
			}
			if((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
				if(sender.hasPermission("staffchat.basic")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cWelcome to &c&lStaffChat v" + getDescription().getVersion()));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDeveloper: &c&lMrIvanPlays"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cList of commands:"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/sc enable &c- Enables StaffChat mode!"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/sc disable &c- Disables StaffChat mode!"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/sc reload &c- Reloads the plugin"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThanks for using &c&lStaffChat"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIf you find bugs, write me on discord: MrIvanPlays#2830"));
					return true;
				}
			}
			Player p = (Player) sender;
			if((args.length == 1) && (args[0].equalsIgnoreCase("enable"))) {
				if(p.hasPermission("staffchat.enable")) {
					lnsc.add(p);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "&cStaffChat enabled!"));
					return true;
				}
			} 
			
			if((args.length == 1) && (args[0].equalsIgnoreCase("disable"))) {
				if(p.hasPermission("staffchat.disable")) {
					lnsc.remove(p);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "&cStaffChat disabled"));
					return true;
				}
			}
			if((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
				if(sender.hasPermission("staffchat.reload")) {
					reloadConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&lPlugin reloaded successfully!"));
					return true;
				}
			}
		}
		return true;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if(lnsc.contains(p)) {
			e.setCancelled(true);
			for(Player staff : Bukkit.getServer().getOnlinePlayers()) {
				if(staff.hasPermission("staffchat.see")) {
					staff.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', "&a" + p.getDisplayName() + " &7»" + "&c " + msg));
				}
			}
		}
	}
}