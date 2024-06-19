package xyz.kozord.torchmclobby.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.command.CommandUse;
import xyz.kozord.torchmclobby.utils.ChatUtil;

import java.util.List;

public class TorchCommand extends CommandUse {

    public TorchCommand(String name, List<String> aliases, String permission) {
        super(name, aliases, permission);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatUtil.color("&cUżycie: &4/tmc <reload>"));
        } else {
            switch (args[0].toLowerCase()) {
                case "rl":
                case "reload":
                    
                    break;
                default:
                    sender.sendMessage(ChatUtil.color("&cUżycie: &4/tmc <reload>"));
                    break;
            }
        }
    }

    @Override
    public List<String> tab(Player player, String[] args) {
        return List.of();
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return TorchMCLobby.getInstance();
    }
}
