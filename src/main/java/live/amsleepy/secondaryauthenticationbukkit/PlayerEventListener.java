package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {

    private final SecondaryAuthentication_Bukkit plugin;

    public PlayerEventListener(SecondaryAuthentication_Bukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PlayerVerificationManager.needsVerification(plugin, player)) {
            player.sendMessage(plugin.getPrefix() + "Please verify yourself using /verify <secret code>");
            plugin.scheduleKick(player);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PlayerVerificationManager.needsVerification(plugin, player)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getPrefix() + "Please verify yourself using /verify <secret code>");
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (PlayerVerificationManager.needsVerification(plugin, player)) {
            String command = event.getMessage().split(" ")[0].toLowerCase();

            // Allow exceptions for /login and /verify commands
            if (command.equals("/login") || command.equals("/verify")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(plugin.getPrefix() + "Please verify yourself using /verify <secret code>");
        }
    }
}