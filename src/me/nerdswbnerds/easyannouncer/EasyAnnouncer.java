package me.nerdswbnerds.easyannouncer;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class EasyAnnouncer extends JavaPlugin {
    public static Logger log;

    public void onEnable(){
        log = getServer().getLogger();

        getCommand("easyannouncer").setExecutor(new EACommandManager(this));

        new EAConfig(this).load();

        EAManager.start(this);
    }

    public void onDisable(){
        new EAConfig(this).save();

        EAManager.stop();
    }
}
