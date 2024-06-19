package xyz.kozord.torchmclobby.modules.messages;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import xyz.kozord.torchmclobby.TorchMCLobby;

import static xyz.kozord.torchmclobby.utils.ChatUtil.color;

public class Chat {

    public static boolean isChatEnabled = false;

    public static void chatEnable(CommandSender sender) {
        if (isChatEnabled) {
            sender.sendMessage(color(TorchMCLobby.getInstance().getMessages().getChatActuallyOn()));
        } else {
            isChatEnabled = true;
            for (String string : TorchMCLobby.getInstance().getMessages().getChatOn()) {
                Bukkit.broadcastMessage(color(string));
            }
        }
    }

    public static void chatDisable(CommandSender sender) {
        if (!isChatEnabled) {
            sender.sendMessage(color(TorchMCLobby.getInstance().getMessages().getChatActuallyOff()));
        } else {
            isChatEnabled = false;
            for (String string : TorchMCLobby.getInstance().getMessages().getChatOff()) {
                Bukkit.broadcastMessage(color(string));
            }
        }
    }

    public static void chatClear() {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage(" ");
        }

        for (String string : TorchMCLobby.getInstance().getMessages().getChatCleared()) {
            Bukkit.broadcastMessage(color(string));
        }
    }
}
