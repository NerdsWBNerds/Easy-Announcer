package me.nerdswbnerds.easyannouncer;

public class EAConfig {
    EasyAnnouncer plugin;

    public EAConfig(EasyAnnouncer ea){
        plugin = ea;
    }

    public void load(){
		plugin.saveDefaultConfig();
        plugin.reloadConfig();

        EAManager.prefix = plugin.getConfig().getString("prefix");
        EAManager.interval  = plugin.getConfig().getInt("interval");
        EAManager.mode = EAMode.getMode(plugin.getConfig().getInt("mode-id"));
        EAManager.messages = plugin.getConfig().getStringList("messages");
    }

    public void save(){
        plugin.getConfig().set("prefix", EAManager.prefix);
        plugin.getConfig().set("interval", EAManager.interval);
        plugin.getConfig().set("mode-id", EAManager.mode.id);
        plugin.getConfig().set("messages", EAManager.messages);

        plugin.saveConfig();
    }
}
