package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerVerificationManager {

    private static final Map<String, Boolean> verifiedPlayers = new HashMap<>();

    // Check if the player needs verification based on the configuration and their verification status
    public static boolean needsVerification(SecondaryAuthentication_Bukkit plugin, Player player) {
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();

        // If the player is not listed in the required_players section, they do not need verification
        if (!config.contains("required_players." + playerName)) {
            return false;
        }

        // The player needs verification if they are not in the verifiedPlayers map or if their value is false
        return !verifiedPlayers.containsKey(playerName) || !verifiedPlayers.get(playerName);
    }

    // Verify the player based on the provided code and the code in the configuration
    public static boolean verifyPlayer(SecondaryAuthentication_Bukkit plugin, Player player, String inputCode) {
        String playerName = player.getName();
        FileConfiguration config = plugin.getConfig();
        String correctCode = config.getString("required_players." + playerName + ".code");
        int duration = config.getInt("required_players." + playerName + ".verificationDuration", 300);

        // If the provided code matches the correct code from the configuration
        if (correctCode != null && correctCode.equals(inputCode)) {
            verifiedPlayers.put(playerName, true);  // Mark the player as verified
            plugin.scheduleVerificationExpiry(player, duration);  // Schedule verification expiry based on duration
            plugin.cancelScheduledKick(player);  // Cancel the scheduled kick if the player verifies successfully
            return true;
        }
        // Return false if verification fails
        return false;
    }

    // Expire the verification status of the player
    public static void expireVerification(Player player) {
        verifiedPlayers.remove(player.getName());
    }
}