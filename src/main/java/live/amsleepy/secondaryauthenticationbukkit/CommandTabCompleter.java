package live.amsleepy.secondaryauthenticationbukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandTabCompleter implements TabCompleter {

    private final SecondaryAuthentication_Bukkit plugin;

    public CommandTabCompleter(SecondaryAuthentication_Bukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("verify")) {
            // For the /verify command, there are no subcommands,
            // and we do not provide specific tab completions for now.
            return suggestions;
        } else if (command.getName().equalsIgnoreCase("secondaryauthentication")) {
            if (args.length == 1) {
                // Provide suggestions for the possible subcommands of /secondaryauthentication
                suggestions = Arrays.asList("reload");
            }
        }

        if (args.length > 0) {
            String currentArg = args[args.length - 1].toLowerCase();
            // Create a new list that contains only the suggestions that start with the currentArg
            return suggestions.stream()
                    .filter(suggestion -> suggestion.toLowerCase().startsWith(currentArg))
                    .collect(Collectors.toList());
        }

        return suggestions;
    }
}