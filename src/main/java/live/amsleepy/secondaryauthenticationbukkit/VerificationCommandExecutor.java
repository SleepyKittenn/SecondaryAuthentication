package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerificationCommandExecutor implements CommandExecutor {

    private final SecondaryAuthentication_Bukkit plugin;

    public VerificationCommandExecutor(SecondaryAuthentication_Bukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(plugin.getPrefix() + "Usage: /verify <secret code>");
            return true;
        }

        String inputCode = args[0];
        if (PlayerVerificationManager.verifyPlayer(plugin, player, inputCode)) {
            player.sendMessage(plugin.getPrefix() + "You have been successfully verified.");
            plugin.cancelScheduledKick(player);  // Cancel the scheduled kick if the player verifies successfully
        } else {
            player.sendMessage(plugin.getPrefix() + "Incorrect verification code. You will be kicked.");
            player.kickPlayer(plugin.getPrefix() + "Incorrect verification code.");
        }
        return true;
    }
}