package xyz.kozord.torchmclobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.kozord.torchmclobby.utils.ChatUtil;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.config.configs.Messages;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandUse extends Command implements PluginIdentifiableCommand {

    public TorchMCLobby plugin;
    public Messages messages;

    public CommandUse(String name, List<String> aliases, String permission) {
        super(name);
        this.plugin = TorchMCLobby.getInstance();
        this.messages = TorchMCLobby.getInstance().getMessages();
        if (aliases != null) {
            this.setAliases(aliases);
        }
        if (permission != null) {
            this.setPermission(permission);
            this.setPermissionMessage(ChatUtil.color(plugin.getMessages().getNoPermissionMessage().replace("%permission%", permission)));
        }
    }

    public abstract void run(CommandSender sender, String[] args);

    public abstract List<String> tab(Player player, String[] args);

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] arguments) {
        if (sender instanceof Player && super.getPermission() != null &&!sender.hasPermission(super.getPermission())) {
            if (super.getPermissionMessage() != null) sender.sendMessage(super.getPermissionMessage());
            return false;
        }

        this.run(sender, arguments);
        return true;
    }

    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        if (!(sender instanceof Player) || (super.getPermission() != null &&!sender.hasPermission(super.getPermission()))) return List.of();
        List<String> completions = this.tab((Player) sender, args);
        return completions == null ? new ArrayList<>() : completions;
    }
}
