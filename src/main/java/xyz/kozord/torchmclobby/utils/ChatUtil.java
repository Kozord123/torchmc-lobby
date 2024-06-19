package xyz.kozord.torchmclobby.utils;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    public static String color(String textToTranslate) {
        if (textToTranslate == null) {
            return "";
        } else {
            Matcher matcher = Pattern.compile("&#([0-9a-fA-F]{6})").matcher(textToTranslate);
            StringBuilder translatedText = new StringBuilder();

            while(matcher.find()) {
                String hexColor = matcher.group(1);
                ChatColor chatColor = hexToChatColor("#" + hexColor);
                matcher.appendReplacement(translatedText, chatColor.toString());
            }

            matcher.appendTail(translatedText);
            return ChatColor.translateAlternateColorCodes('&', translatedText.toString());
        }
    }

    public static List<String> colorList(List<String> lore) {
        ArrayList<String> fixLore = new ArrayList();
        if (lore == null) {
            return fixLore;
        } else {
            lore.forEach((s) -> {
                fixLore.add(color(s));
            });
            return fixLore;
        }
    }

    private static ChatColor hexToChatColor(String hexColor) {
        return ChatColor.of(hexColor);
    }

    public static void sendActionbar(Player player, String message) {
        if (player != null && message != null) {
            String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
            if (!(nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1)).startsWith("v1_9_R") && !nmsVersion.startsWith("v1_8_R")) {
                player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(message)));
            } else {
                try {
                    Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
                    Object craftPlayer = craftPlayerClass.cast(player);
                    Class<?> ppoc = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
                    Class<?> packet = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
                    Class<?> chat = Class.forName("net.minecraft.server." + nmsVersion + (nmsVersion.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
                    Class<?> chatBaseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                    Method method = null;
                    if (nmsVersion.equalsIgnoreCase("v1_8_R1")) {
                        method = chat.getDeclaredMethod("a", String.class);
                    }

                    Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(Objects.requireNonNull(method).invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(String.class).newInstance(message);
                    Object packetPlayOutChat = ppoc.getConstructor(chatBaseComponent, Byte.TYPE).newInstance(object, (byte)2);
                    Method handle = craftPlayerClass.getDeclaredMethod("getHandle");
                    Object iCraftPlayer = handle.invoke(craftPlayer);
                    Field playerConnectionField = iCraftPlayer.getClass().getDeclaredField("playerConnection");
                    Object playerConnection = playerConnectionField.get(iCraftPlayer);
                    Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", packet);
                    sendPacket.invoke(playerConnection, packetPlayOutChat);
                } catch (Exception e) {
                    Bukkit.getLogger().severe("Wystąpił błąd podczas wysyłania actionBara: " + e.getMessage());
                }

            }
        }
    }

    public static void sendTitle(Player p, String t, String s) {
        p.sendTitle(color(t), color(s));
    }
}
