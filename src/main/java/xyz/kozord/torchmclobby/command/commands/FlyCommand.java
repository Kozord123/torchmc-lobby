package xyz.kozord.torchmclobby.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.command.CommandUse;
import xyz.kozord.torchmclobby.utils.ChatUtil;
import xyz.kozord.torchmclobby.TorchMCLobby;

import java.util.List;
import java.util.stream.Collectors;

public class FlyCommand extends CommandUse {

    public FlyCommand(String name, List<String> aliases, String permission) {
        super(name, aliases, permission);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage("Nie mozesz tego zrobic jako konsola!");
                return;
            }

            if (p.getAllowFlight()) {
                p.sendMessage(ChatUtil.color(messages.getFlyDisabled()));
                toggleFlight(p, false);
            } else {
                p.sendMessage(ChatUtil.color(messages.getFlyEnabled()));
                toggleFlight(p, true);
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(ChatUtil.color(messages.getThisPlayerIsOffline()));
                return;
            }

            if (target.getAllowFlight()) {
                sender.sendMessage(ChatUtil.color(messages.getFlyDisabledForSomeone()).replace("%player%", target.getName()));
                toggleFlight(target, false);
                if (target.hasPermission("torchmclobby.fly.use")) target.sendMessage(ChatUtil.color(messages.getFlyDisabledForYou()).replace("%player", sender.getName()));
            } else {
                sender.sendMessage(ChatUtil.color(messages.getFlyEnabledForSomeone()).replace("%player%", target.getName()));
                toggleFlight(target, true);
                if (target.hasPermission("torchmclobby.fly.use")) target.sendMessage(ChatUtil.color(messages.getFlyEnabledForYou()).replace("%player", sender.getName()));
            }
        }

    }

    private void toggleFlight(Player player, boolean value) {
        player.setAllowFlight(value);
        player.setFlying(value);
    }

    @Override
    public List<String> tab(Player player, String[] args) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return TorchMCLobby.getInstance();
    }
}
