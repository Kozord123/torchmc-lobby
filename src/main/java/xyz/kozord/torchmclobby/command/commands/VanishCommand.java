package xyz.kozord.torchmclobby.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.command.CommandUse;
import xyz.kozord.torchmclobby.modules.vanish.Vanish;
import xyz.kozord.torchmclobby.TorchMCLobby;

import java.util.List;

public class VanishCommand extends CommandUse {


    public VanishCommand(String name, List<String> aliases, String permission) {
        super(name, aliases, permission);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Nie mozesz tego zrobic jako konsola!");
            return;
        }

        Vanish.toggleVanish(player);
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
