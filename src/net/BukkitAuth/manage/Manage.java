package net.BukkitAuth.manage;

import java.util.Map;

import net.BukkitAuth.DataBase;
import net.BukkitAuth.Main;
import net.BukkitPE.Player;
import net.BukkitPE.Server;
import net.BukkitPE.command.CommandSender;

public class Manage {
	private Register registerManager;
	private Login loginManager;
	private Main plugin;
	
	public Manage(Main plugin) {
		this.plugin = plugin;
		registerManager = new Register(plugin);
		loginManager = new Login(plugin);
	}
	public DataBase getDataBase() {
		return plugin.getDataBase();
	}
	public boolean isLogin(String player) {
		return isLogin(Server.getInstance().getPlayer(player));
	}
	public boolean isLogin(CommandSender player) {
		return loginManager.isLogin(player);
	}
	public boolean isRegister(String player) {
		return registerManager.isRegister(player);
	}
	public boolean isRegister(CommandSender player) {
		return isRegister(player.getName());
	}
	public void setRegister(CommandSender sender, String password) {
		try {
			registerManager.setRegister(sender, password);
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	public void setRegister(String sender, String password) {
		setRegister(Server.getInstance().getPlayer(sender), password);
	}
	public boolean checkPassword(CommandSender sender, String password) {
		return loginManager.checkPassword(sender, password);
	}
	public boolean checkPassword(String sender, String password) {
		return checkPassword(sender, password);
	}
	public void setLogin(CommandSender sender, boolean bool) {
		loginManager.setLogin(sender, bool);
	}
	public boolean changePassword(String target, String password) {
		return loginManager.changePassword(target, password);
	}
	public boolean changePassword(CommandSender target, String password) {
		return changePassword(target.getName().toLowerCase(), password);
	}
	public boolean unRegister(String target) {
		return registerManager.unRegister(target);
	}
	public boolean unRegister(CommandSender target) {
		return unRegister(target);
	}
	public boolean isAllowSubAccount() {
		return (boolean) getDataBase().config.get("allow-subaccount");
	}
	public void setAllowSubAccount(boolean bool) {
		getDataBase().config.set("allow-subaccount", bool);
	}
	@SuppressWarnings("unchecked")
	public String getSubAccount(String player) {
		for (Object account : getDataBase().accountDB.values()) {
			String cid = String.valueOf(getDataBase().getClientId(Server.getInstance().getPlayer(player)));
			if (((Map<String,String>) account).get("clientid").equals(cid)) {
				String name = ((Map<String, String>)account).get("name");
				if (name.equals(player.toLowerCase())) {
					continue;
				} else {
					return name;
				}
			}
		}
		return null;
	}
	public String getSubAccount(Player player) {
		return getSubAccount(player.getName());
	}
}