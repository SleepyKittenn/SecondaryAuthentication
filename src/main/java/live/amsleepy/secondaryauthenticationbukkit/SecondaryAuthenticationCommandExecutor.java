package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SecondaryAuthenticationCommandExecutor implements CommandExecutor {

    private final SecondaryAuthentication_Bukkit plugin;

    public SecondaryAuthenticationCommandExecutor(SecondaryAuthentication_Bukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("secondaryauthentication.reload")) {
                plugin.reloadPluginConfig();
                sender.sendMessage(plugin.getPrefix() + "Configuration reloaded.");
            } else {
                sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You don't have permission to use this command.");
            }
            return true;
        }

        sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Usage: /" + label + " reload");
        return true;
    }
}