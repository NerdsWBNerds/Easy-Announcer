package me.nerdswbnerds.easyannouncer;

import java.io.*;

public class EAConfig {
    EasyAnnouncer plugin;

    public EAConfig(EasyAnnouncer ea){
        plugin = ea;
    }

    public void load(){
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
        plugin.reloadConfig();

        EAManager.prefix = plugin.getConfig().getString("prefix");
        EAManager.interval  = plugin.getConfig().getInt("interval");
        EAManager.mode = EAMode.getMode(plugin.getConfig().getInt("mode-id"));
        EAManager.messages = plugin.getConfig().getStringList("messages");
    }

    public void save(){
		FileOutputStream fos = null;
		try{
			File file = new File(plugin.getDataFolder().getPath() + "/config.yml");
			if(file.exists())
				file.delete();

			if(!file.exists()){
				file.createNewFile();
			}

			fos = new FileOutputStream(file.getPath());
			PrintWriter out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(fos, "UTF-8")));

			out.println("#################################");
			out.println("##  === [ EASY ANNOUNCER ] === ##");
			out.println("#################################");
			out.println("");
			out.println("# Prefix of all announcements.");
			out.println("# ex. [info] Your announcement here.");
			out.println("prefix: \"" + EAManager.prefix + "\"");
			out.println("");
			out.println("# Interval (in seconds) between announcements.");
			out.println("interval: " + EAManager.interval);
			out.println("");
			out.println("# Announcement mode, use '/easyannouncer help modes' for more info.");
			out.println("mode-id: " + EAManager.mode.id);
			out.println("");
			out.println("messages:");
			for(String m: EAManager.messages){
				out.println("- " + m);
			}

			out.flush();
			out.close();
		}catch(Exception e){

		}
    }
}
