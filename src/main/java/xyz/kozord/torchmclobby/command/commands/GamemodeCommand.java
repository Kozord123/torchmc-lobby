package xyz.kozord.torchmclobby.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.command.CommandUse;
import xyz.kozord.torchmclobby.utils.ChatUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCommand extends CommandUse {
    public GamemodeCommand(String command, List<String> aliases, String permission) {
        super(command, aliases, permission);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length > 0) {
            GameMode gameMode = null;
            switch (args[0]) {
                case "survival":
                case "s":
                case "0":
                    gameMode = GameMode.SURVIVAL;
                    break;
                case "creative":
                case "c":
                case "1":
                    gameMode = GameMode.CREATIVE;
                    break;
                case "adventure":
                case "a":
                case "2":
                    gameMode = GameMode.ADVENTURE;
                    break;
                case "spectator":
                case "sp":
                case "3":
                    gameMode = GameMode.SPECTATOR;
                    break;
                default:
                    break;
            }

            if (gameMode != null) {
                if (args.length == 1) {
                    if (sender instanceof Player p) {
                        p.setGameMode(gameMode);
                        p.sendMessage(ChatUtil.color(messages.getGamemodeChangeSelf().replace("%gamemode%", gameMode.name().toUpperCase())));
                    } else {
                        sender.sendMessage("Nie mozesz tego zrobic jako konsola!");
                    }
                } else {
                    if (args[1].equalsIgnoreCase("*")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.setGameMode(gameMode);
                            if (p.hasPermission("torchmc.gamemode.use"))
                                p.sendMessage(ChatUtil.color(messages.getGamemodeSomeoneChangedYou().replace("%gamemode%", gameMode.name().toUpperCase()).replace("%player%", sender.getName())));
                        }
                        sender.sendMessage(ChatUtil.color(messages.getGamemodeChangedForSomeone().replace("%gamemode%", gameMode.name().toUpperCase()).replace("%player%", "*")));
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null && target.isOnline()) {
                            target.setGameMode(gameMode);
                            if (target.hasPermission("torchmc.gamemode.use"))
                                target.sendMessage(ChatUtil.color(messages.getGamemodeSomeoneChangedYou().replace("%gamemode%", gameMode.name().toUpperCase()).replace("%player%", sender.getName())));
                            sender.sendMessage(ChatUtil.color(messages.getGamemodeChangedForSomeone().replace("%gamemode%", gameMode.name().toUpperCase()).replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatUtil.color(messages.getThisPlayerIsOffline()));
                        }
                    }
                }
            } else {
                sender.sendMessage(messages.getGamemodeCommandUsage());
            }
        } else {
            sender.sendMessage(messages.getGamemodeCommandUsage());
        }
    }

    @Override
    public List<String> tab(Player player, String[] args) {
        if (args.length == 1) {
            return List.of("0", "1", "2", "3", "s", "survival", "c", "creative", "a", "adventure", "sp", "spectator");
        } else if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return TorchMCLobby.getInstance();
    }
}
