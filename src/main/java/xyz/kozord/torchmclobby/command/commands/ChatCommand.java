package xyz.kozord.torchmclobby.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.command.CommandUse;
import xyz.kozord.torchmclobby.modules.messages.Chat;
import xyz.kozord.torchmclobby.TorchMCLobby;

import java.util.List;

public class ChatCommand extends CommandUse {

    public ChatCommand(String name, List<String> aliases, String permission) {
        super(name, aliases, permission);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Użycie: /chat <on/off/clear>");
        } else {
            switch (args[0]) {
                case "on":
                    Chat.chatEnable(sender);
                    break;
                case "off":
                    Chat.chatDisable(sender);
                    break;
                case "clear":
                    Chat.chatClear();
                    break;
                default:
                    sender.sendMessage("Użycie: /chat <on/off/clear>");
                    break;
            }
        }
    }

    @Override
    public List<String> tab(Player player, String[] args) {
        return List.of("on", "off", "clear");
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return TorchMCLobby.getInstance();
    }
}
