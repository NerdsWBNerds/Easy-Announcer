package me.nerdswbnerds.easyannouncer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EACommandManager implements CommandExecutor {
	EasyAnnouncer plugin;

	public EACommandManager(EasyAnnouncer ea){
		plugin = ea;
	}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
        if(cmd.getName().equalsIgnoreCase("easyannouncer")){
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Error: /" + label + " help");
                return true;
            }

            if(args[0].equalsIgnoreCase("prefix")){
                String reqPerm = "easyannouncer.commands.prefix";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

                if(args.length <= 1){
					sender.sendMessage(ChatColor.AQUA + "To update the prefix, use /" + label + " prefix <new-prefix>");
					sender.sendMessage(ChatColor.AQUA + "The prefix currently is " + EAManager.prefix);
					return true;
                }

                StringBuilder sb = new StringBuilder(args[1]);

                for(int i = 2; i < args.length; i++){
                    sb.append(args[i]);
                }

                EAManager.prefix = sb.toString();

                sender.sendMessage(ChatColor.AQUA + "Prefix updated to '" + ChatColor.GREEN + EAManager.prefix + ChatColor.AQUA + "'");
                return true;
            }

            if(args[0].equalsIgnoreCase("interval")){
                String reqPerm = "easyannouncer.commands.interval";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }


                if(args.length == 2){
                    boolean broken = false;
                    int newInt = EAManager.interval;

                    try{
                        newInt = Integer.parseInt(args[1]);
                    }catch(Exception e){
                        broken = true;
                    }

                    if(!broken){
                        EAManager.interval = newInt;
                        sender.sendMessage(ChatColor.AQUA + "Interval updated to " + ChatColor.GREEN + EAManager.interval + " second(s).");
                        return true;
                    }
                }

				sender.sendMessage(ChatColor.AQUA + "To update the interval, use /" + label + " interval <new-interval>");
				sender.sendMessage(ChatColor.AQUA + "Interval currently set to " + ChatColor.GREEN + EAManager.interval + " second(s).");
                return true;
            }

            if(args[0].equalsIgnoreCase("add")){
                String reqPerm = "easyannouncer.commands.add";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

                if(args.length <= 1){
                    sender.sendMessage(ChatColor.RED + "Error: " + label + " add <new announcement>");
                    return true;
                }

                StringBuilder sb = new StringBuilder(args[1]);

                for(int i = 2; i < args.length; i++){
                     sb.append(args[i]);
                }

                EAManager.addMessage(sb.toString());

                sender.sendMessage(ChatColor.GREEN + "Added: " + EAManager.getLastPrefixColor() + (EAManager.parseColor(sb.toString())));
				return true;
            }

            if(args[0].equalsIgnoreCase("remove")){
                String reqPerm = "easyannouncer.commands.remove";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

                if(args.length <= 1){
                    sender.sendMessage(ChatColor.RED + "Error: " + label + " remove <announcement-id>");
                    return true;
                }

				int toRemove = 0;

				try{
					toRemove = Integer.parseInt(args[1]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "Error: ID must be all integers");
					return true;
				}

				toRemove--;

				String message = EAManager.getLastPrefixColor() + EAManager.getMessage(toRemove);
				EAManager.removeMessage(toRemove);

				sender.sendMessage(ChatColor.RED + "Removed: " + message);
				return true;
            }

            if(args[0].equalsIgnoreCase("mode")){
                String reqPerm = "easyannouncer.commands.mode";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

                if(args.length <= 1){
					sender.sendMessage(ChatColor.AQUA + "To update the mode, use /" + label + " mode <mode-id>");
					sender.sendMessage(ChatColor.AQUA + "The mode is currently set to " + ChatColor.GREEN + EAManager.mode.id + " (" + EAManager.mode.toString() + ")");
                    return true;
                }

				int modeID = 0;

				try{
					modeID = Integer.parseInt(args[1]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "Error: Mode ID must be an integer,");
					return true;
				}

				if(modeID < 1 || modeID > 3){
					sender.sendMessage(ChatColor.RED + "Error: Mode ID can only be 1, 2, or 3.");
					return  true;
				}

				EAMode mode = EAMode.getMode(modeID);

				sender.sendMessage(ChatColor.AQUA + "Mode updated to " + ChatColor.GREEN + modeID + " (" + mode.toString() + ")");
				return true;
            }

            if(args[0].equalsIgnoreCase("say")){
                String reqPerm = "easyannouncer.commands.say";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

                if(args.length <= 1){
                    sender.sendMessage(ChatColor.RED + "Error: " + label + " say <announcement-id>");
                    return true;
                }

				int toSay = 0;

				try{
					toSay = Integer.parseInt(args[1]);
				}catch(Exception e){
					sender.sendMessage(ChatColor.RED + "Error: ID must be all integers");
					return true;
				}

				toSay --;

				if(toSay < 0 || toSay >= EAManager.messages.size()){
					sender.sendMessage(ChatColor.RED + "Error: ID must be between 1 (inclusive) and " + EAManager.messages.size() + " (inclusive).");
					return true;
				}

				EAManager.announce(toSay);

				if(!sender.hasPermission("easyannouncer.view") || !(sender instanceof Player)){
					sender.sendMessage(ChatColor.AQUA + "SAID: " + EAManager.getMessage(toSay));
				}

				return true;
            }

            if(args[0].equalsIgnoreCase("start")){
                String reqPerm = "easyannouncer.commands.start";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

				if(EAManager.start(plugin)){
					sender.sendMessage(ChatColor.AQUA + "Easy Announcer " + ChatColor.GREEN + "started.");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Error: Easy Announcer already running.");
					return true;
				}
            }

			if(args[0].equalsIgnoreCase("stop")){
				String reqPerm = "easyannouncer.commands.stop";

				if(!sender.hasPermission(reqPerm)){
					sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
					return true;
				}

				if(EAManager.stop()){
					sender.sendMessage(ChatColor.AQUA + "Easy Announcer " + ChatColor.RED + "stopped.");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Error: Easy Announcer already stopped.");
					return true;
				}
			}

			if(args[0].equalsIgnoreCase("status")){
				String reqPerm = "easyannouncer.commands.status";

				if(!sender.hasPermission(reqPerm)){
					sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
					return true;
				}

				sender.sendMessage(ChatColor.AQUA + "Easy Announcer is " + ((EAManager.isRunning())?(ChatColor.GREEN + "running."):(ChatColor.RED + "stopped.")));
				return true;
			}

            if(args[0].equalsIgnoreCase("list")){
                String reqPerm = "easyannouncer.commands.list";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }


				sender.sendMessage("");
				sender.sendMessage(ChatColor.GOLD + "===[ " + ChatColor.RED + "EASY ANNOUNCER LIST " + ChatColor.GOLD + "]===");

				for(int i = 0; i < EAManager.messages.size(); i++){
					String msg = EAManager.parseColor(EAManager.getMessage(i));

					String pre = EAManager.getLastPrefixColor();

					sender.sendMessage(ChatColor.GREEN + "[" + (i + 1) + "] " + pre + msg);
				}

				sender.sendMessage("");
            }

            if(args[0].equalsIgnoreCase("reload")){
                String reqPerm = "easyannouncer.commands.reload";

                if(!sender.hasPermission(reqPerm)){
                    sender.sendMessage(ChatColor.RED + "Error: You must have the permission node '" + reqPerm + "' to do this.");
                    return true;
                }

				new EAConfig(plugin).load();
				sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
				return true;
            }

            if(args[0].equalsIgnoreCase("help")){
                if(args.length == 2){
                    if(args[1].equalsIgnoreCase("cmd") || args[1].equalsIgnoreCase("cmds") || args[1].equalsIgnoreCase("command") || args[1].equalsIgnoreCase("commands")){
                        if(!sender.hasPermission("easyannouncer.help.commands")){
                            sender.sendMessage(ChatColor.RED + "Error: You must have the permission node 'easyannouncer.help.commands' to do this.");
                            return true;
                        }

                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.GOLD + "===[ " + ChatColor.RED + "EASY ANNOUNCER COMMANDS " + ChatColor.GOLD + "]===");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " prefix <new-prefix>" + ChatColor.GREEN + " - Set announcer prefix which comes before each announcement.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " interval <time>" + ChatColor.GREEN + " - Set the amount of seconds before each announcement is made.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " add <message>" + ChatColor.GREEN + " - Add an announcement to the current list of messages.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " remove <id>" + ChatColor.GREEN + " - Remove existing announcement by ID, use /announcer list to get IDs.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " mode <id>" + ChatColor.GREEN + " - Set the announcer mode, use /announcer help modes to get mode IDs.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " say <id>" + ChatColor.GREEN + " - Say an existing announcement, use /announcer list to get mode IDs.");;
						sender.sendMessage(ChatColor.AQUA + "/" + label + " status" + ChatColor.GREEN + " - Check is Easy Announcer is running or stopped.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " start" + ChatColor.GREEN + " - Start the announcer.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " stop" + ChatColor.GREEN + " - Stop the announcer.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " list" + ChatColor.GREEN + " -  List current messages ordered by ID.");
                        sender.sendMessage(ChatColor.AQUA + "/" + label + " reload" + ChatColor.GREEN + " -  Reload settings from the config.");
                        sender.sendMessage("");
                    }

                    if(args[1].equalsIgnoreCase("type") || args[1].equalsIgnoreCase("types") || args[1].equalsIgnoreCase("mode") || args[1].equalsIgnoreCase("modes")){
                        if(!sender.hasPermission("easyannouncer.help.modes")){
                            sender.sendMessage(ChatColor.RED + "Error: You must have the permission node easyannouncer.help.modes to do this.");
                            return true;
                        }

                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.GOLD + "===[ " + ChatColor.RED + "EASY ANNOUNCER MODES " + ChatColor.GOLD + "]===");
                        sender.sendMessage(ChatColor.AQUA + "[MODE 1] " + ChatColor.GREEN + " Announcements are made in order by ID.");
                        sender.sendMessage(ChatColor.AQUA + "[MODE 2] " + ChatColor.GREEN + " Announcements are chosen at random.");
                        sender.sendMessage(ChatColor.AQUA + "[MODE 3] " + ChatColor.GREEN + " Announcements are chosen at random but do not repeat.");
                        sender.sendMessage("");

                        return true;
                    }
                }else{
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.GOLD + "===[ " + ChatColor.RED + "EASY ANNOUNCER HELP " + ChatColor.GOLD + "]===");
                    sender.sendMessage(ChatColor.AQUA + "/announcer help cmds"  + ChatColor.GREEN + " - Get help with different announcer commands");
                    sender.sendMessage(ChatColor.AQUA + "/announcer help modes"  + ChatColor.GREEN + " - Get help with different announcer types/modes");
                    sender.sendMessage("");
                }
            }
        }

        return false;
    }
}
