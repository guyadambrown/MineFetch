package xyz.guyb;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.guyb.commands.fetch;

import java.util.Objects;

public class MineFetch extends JavaPlugin {
    public static MineFetch plugin;
    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        getLogger().info("MineFetch is enabled");
        plugin = this;
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("fetch")).setExecutor(new fetch());
    }
}
