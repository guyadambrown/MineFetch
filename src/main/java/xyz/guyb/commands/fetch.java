package xyz.guyb.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import oshi.SystemInfo;
import xyz.guyb.MineFetch;

import java.lang.management.ManagementFactory;
import java.time.Duration;

public class fetch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        FileConfiguration config = MineFetch.plugin.getConfig();
        String componentColour = config.getString("colours.component");
        String valueColour = config.getString("colours.value");
        if (!(strings.length <= 1)){
            if (strings[0].contains("&")){
                componentColour = ChatColor.translateAlternateColorCodes('&', strings[0]);
            }

            if (strings[1].contains("&")){
                valueColour = ChatColor.translateAlternateColorCodes('&', strings[1]);
            }
        }



        if (commandSender.hasPermission("MineFetch.fetch")) {

            String userName = System.getProperty("user.name");
            String hostName = new SystemInfo().getOperatingSystem().getNetworkParams().getHostName();
            String userNameAndHostname = userName + "@" + hostName;
            String dashString = generateDashes(userNameAndHostname);
            String operatingSystem = new  SystemInfo().getOperatingSystem().getFamily() +" " + new SystemInfo().getOperatingSystem().getVersionInfo().getVersion();
            String kernel = new SystemInfo().getOperatingSystem().getVersionInfo().getBuildNumber();
            String codename = new SystemInfo().getOperatingSystem().getVersionInfo().getCodeName();
            Duration sysUptime = Duration.ofSeconds(new SystemInfo().getOperatingSystem().getSystemUptime());
            String sysUptimeFormatted = sysUptime.toHours() + " hours, " + sysUptime.toMinutesPart()+ " mins, " + sysUptime.toSecondsPart() + " secs";
            Duration jvmUpTime = Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
            String jvmUptimeFormatted = jvmUpTime.toHours() + " hours, " + jvmUpTime.toMinutesPart()+ " mins, " + jvmUpTime.toSecondsPart() + " secs";
            String plugins = String.valueOf(Bukkit.getPluginManager().getPlugins().length);
            // String shell = Bukkit.getBukkitVersion();
            String centralProcessor = new SystemInfo().getHardware().getProcessor().getProcessorIdentifier().getName();
            if (centralProcessor.length() == 0){
                centralProcessor = "unknown";
            }
            String GPU = new SystemInfo().getHardware().getGraphicsCards().get(0).getName();
            long MEGABYTE = 1024L * 1024L;
            Long usedMemory =  new SystemInfo().getHardware().getMemory().getTotal() / MEGABYTE - new  SystemInfo().getHardware().getMemory().getAvailable() / MEGABYTE;
            long totalMemory = new SystemInfo().getHardware().getMemory().getTotal() / MEGABYTE;
            String memUsage = usedMemory + "MB / " + totalMemory+ "MB";
            // String systemModel = new SystemInfo().getHardware().getComputerSystem().getModel();



            commandSender.sendMessage(componentColour+ userName + valueColour + "@" + componentColour + hostName);
            commandSender.sendMessage(dashString);

            if (codename.length() == 0){
                commandSender.sendMessage(componentColour + "OS: " + valueColour + operatingSystem + ", " + kernel);
            }else {
                commandSender.sendMessage(componentColour + "OS: " + valueColour + operatingSystem + "("+ codename+ "), " + kernel);
            }
            commandSender.sendMessage(componentColour + "System Uptime: "+ valueColour + sysUptimeFormatted);
            commandSender.sendMessage(componentColour + "JVM Uptime: "+ valueColour + jvmUptimeFormatted);
            commandSender.sendMessage(componentColour + "Plugins: " + valueColour + plugins);
            if (!centralProcessor.contains("unknown")){
                commandSender.sendMessage(componentColour + "CPU: " + valueColour + centralProcessor);
            }

            if (!GPU.contains("unknown")){
                commandSender.sendMessage(componentColour + "GPU: " + valueColour + GPU);
            }
            commandSender.sendMessage(componentColour + "Memory: " + valueColour + memUsage);
        }



        return true;
    }

    public static String generateDashes(String x) {
        int length = x.length();
        return "-".repeat(length);
    }

}
