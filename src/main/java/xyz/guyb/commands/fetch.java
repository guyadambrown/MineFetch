package xyz.guyb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.guyb.MineFetch;

import java.io.IOException;
import java.util.logging.Logger;

public class fetch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        FileConfiguration config = MineFetch.plugin.getConfig();
        String hostname = null;
        if (commandSender.hasPermission("MineFetch.fetch")) {
            try {
                hostname = Runtime.getRuntime().exec("hostname").toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Logger.getGlobal().info(hostname);

        return true;
    }
}
