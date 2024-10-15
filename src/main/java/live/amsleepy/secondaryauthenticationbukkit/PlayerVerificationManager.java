package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerVerificationManager {

    private static final Map<String, Boolean> verifiedPlayers = new HashMap<>();

    public static boolean needsVerification(SecondaryAuthentication_Bukkit plugin, Player player) {
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();

        if (!config.contains("required_players." + playerName)) {
            return false;
        }

        return !verifiedPlayers.containsKey(playerName) || !verifiedPlayers.get(playerName);
    }

    public static boolean verifyPlayer(SecondaryAuthentication_Bukkit plugin, Player player, String inputCode) {
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();
        String correctCode = config.getString("required_players." + playerName + ".code");
        int duration = config.getInt("required_players." + playerName + ".verificationDuration", 300);

        if (correctCode != null && correctCode.equals(inputCode)) {
            verifiedPlayers.put(playerName, true);
            plugin.scheduleVerificationExpiry(player, duration);
            plugin.cancelScheduledKick(player);
            return true;
        }

        return false;
    }

    public static void expireVerification(Player player) {
        verifiedPlayers.remove(player.getName());
    }
}