package me.nerdswbnerds.easyannouncer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EAManager {
    public static List<String> messages = new ArrayList<String>();

    public static String prefix = "&a[Info]&c";
    public static int interval = 60;
    public static EAMode mode = EAMode.ORDERED;

    public static EATimer timer = null;

	public static boolean start(EasyAnnouncer ea){
        if(timer != null)
            return false;

        timer = new EATimer();
        timer.id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ea, timer, 20L, 20L);

		return true;
    }

    public static boolean stop(){
        if(timer == null)
            return false;

        Bukkit.getServer().getScheduler().cancelTask(timer.id);
        timer = null;

		return true;
    }

    public static void addMessage(String s){
        messages.add(s);
    }

    public static void removeMessage(int i){
        messages.remove(i);
    }

    public static String getMessage(int id){
        return parseColor(messages.get(id));
    }

    public static String parseColor(String s){
        String message = s;

        message = message.replaceAll("&0", ChatColor.BLACK + "");
        message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
        message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
        message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
        message = message.replaceAll("&4", ChatColor.DARK_RED + "");
        message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        message = message.replaceAll("&6", ChatColor.GOLD + "");
        message = message.replaceAll("&7", ChatColor.GRAY + "");
        message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
        message = message.replaceAll("&9", ChatColor.BLUE + "");
        message = message.replaceAll("&a", ChatColor.GREEN + "");
        message = message.replaceAll("&b", ChatColor.AQUA + "");
        message = message.replaceAll("&c", ChatColor.RED + "");
        message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        message = message.replaceAll("&e", ChatColor.YELLOW + "");
        message = message.replaceAll("&f", ChatColor.WHITE + "");
        message = message.replaceAll("&k", ChatColor.MAGIC + "");
        message = message.replaceAll("&m", ChatColor.MAGIC + "");
        message = message.replaceAll("&i", ChatColor.ITALIC + "");
        message = message.replaceAll("&u", ChatColor.UNDERLINE + "");
        message = message.replaceAll("&h", ChatColor.BOLD + "");
        message = message.replaceAll("&n", ChatColor.RESET + "");
        message = message.replaceAll("&r", ChatColor.RESET + "");

        return message;
    }

    public static void announce(int id) {
        if(id >= messages.size()){
            id = 0;
        }

        if (messages.size() == 0){
            return;
        }

        String message = getMessage(id);
        String full = parseColor(prefix) + message;

        for(Player p: Bukkit.getOnlinePlayers()){
            if(p.hasPermission("easyannouncer.view"))
                p.sendMessage(full);
        }
    }

	public static String getLastPrefixColor() {
		String toRet = prefix;

		boolean broken = false;

		try{
			toRet = toRet.trim();
			toRet = toRet.substring(toRet.length() - 2, toRet.length());
			toRet = parseColor(toRet);
		}catch (Exception e){
			broken = true;
		}

		return (broken)?ChatColor.RED + "":toRet;
	}

	public static boolean isRunning() {
		return (timer != null);
	}
}
