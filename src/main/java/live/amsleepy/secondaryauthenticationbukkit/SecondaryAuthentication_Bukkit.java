package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public final class SecondaryAuthentication_Bukkit extends JavaPlugin {

    private final String prefix = ChatColor.DARK_PURPLE + "[SecAuth] " + ChatColor.WHITE;
    private FileConfiguration config;
    private int verificationTimeout;

    private final Map<String, BukkitTask> kickTasks = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        verificationTimeout = config.getInt("global_settings.verificationTimeout", 60);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);

        PluginCommand verifyCommand = getCommand("verify");
        if (verifyCommand != null) {
            verifyCommand.setExecutor(new VerificationCommandExecutor(this));
            verifyCommand.setTabCompleter(new CommandTabCompleter(this));   // Register tab completer for /verify
        } else {
            getLogger().severe(prefix + "Command 'verify' is not defined in plugin.yml");
        }

        PluginCommand secAuthCommand = getCommand("secondaryauthentication");
        if (secAuthCommand != null) {
            secAuthCommand.setExecutor(new SecondaryAuthenticationCommandExecutor(this));
            secAuthCommand.setTabCompleter(new CommandTabCompleter(this));  // Register tab completer for /secondaryauthentication
        } else {
            getLogger().severe(prefix + "Command 'secondaryauthentication' is not defined in plugin.yml");
        }

        getLogger().info(prefix + "SecondaryAuthentication-Bukkit enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(prefix + "SecondaryAuthentication-Bukkit disabled.");
    }

    public void scheduleVerificationExpiry(Player player, int duration) {
        if (duration > 0) {
            getServer().getScheduler().runTaskLater(this, () -> PlayerVerificationManager.expireVerification(player), duration * 20L);
        }
    }

    public void scheduleKick(Player player) {
        if (verificationTimeout > 0) {
            BukkitTask task = getServer().getScheduler().runTaskLater(this, () -> {
                player.kickPlayer(prefix + "You did not verify in time.");
                kickTasks.remove(player.getName());
            }, verificationTimeout * 20L);
            kickTasks.put(player.getName(), task);
        }
    }

    public void cancelScheduledKick(Player player) {
        BukkitTask task = kickTasks.remove(player.getName());
        if (task != null) {
            task.cancel();
        }
    }

    public void reloadPluginConfig() {
        reloadConfig();
        config = getConfig();
        verificationTimeout = config.getInt("global_settings.verificationTimeout", 60);
        getLogger().info(prefix + "Configuration reloaded.");
    }

    public String getPrefix() {
        return prefix;
    }
}