package xyz.kozord.torchmclobby.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Message {

    public static void message(Player player, List<String> types, List<String> messages, Map<String, String> replacements) {
        types.forEach(String::toUpperCase);

        List<Byte> bytes = new ArrayList<>();

        types.forEach(type -> {
            switch (type) {
                case "TITLE":
                    bytes.add((byte) 0);
                    break;
                case "TITLE_SUBTITLE":
                    bytes.add((byte) 1);
                    break;
                case "SUBTITLE":
                    bytes.add((byte) 2);
                    break;
                case "ACTIONBAR":
                    bytes.add((byte) 3);
                    break;
                case "CHAT":
                    bytes.add((byte) 4);
                    break;
            }
        });

        if (bytes.contains((byte) 4)) {
            messages.forEach(s -> player.sendMessage(ChatUtil.color(replacePlaceholders(s, replacements))));
        }

        String message = messages.get(0);

        if (bytes.contains((byte) 0)) ChatUtil.sendTitle(player, replacePlaceholders(message, replacements), "");
        if (bytes.contains((byte) 1)) ChatUtil.sendTitle(player, message, replacePlaceholders(message, replacements));
        if (bytes.contains((byte) 2)) ChatUtil.sendTitle(player, "", replacePlaceholders(message, replacements));
        if (bytes.contains((byte) 3)) ChatUtil.sendActionbar(player, ChatUtil.color(replacePlaceholders(message, replacements)));
    }

    private static String replacePlaceholders(String message, Map<String, String> replacements) {
        if (replacements != null && !replacements.isEmpty()) {
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                message = message.replaceAll(entry.getKey(), entry.getValue());
            }
        }
        return message;
    }
}
