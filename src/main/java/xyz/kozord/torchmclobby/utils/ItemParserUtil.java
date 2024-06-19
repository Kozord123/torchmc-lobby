package xyz.kozord.torchmclobby.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.config.ItemBuilder;
import xyz.kozord.torchmclobby.managers.ServerManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static xyz.kozord.torchmclobby.utils.ChatUtil.color;
import static xyz.kozord.torchmclobby.utils.ChatUtil.colorList;

public class ItemParserUtil {

    public static ItemStack parseItem(ItemBuilder itemBuilder) {
        ItemStack itemStack = new ItemStack(itemBuilder.getItemStack().getType(), itemBuilder.getItemStack().getAmount());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(color(itemBuilder.getDisplayName()));
        itemMeta.setLore(colorList(itemBuilder.getLore()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack parseServerItem(String serverName, ItemBuilder itemBuilder) {
        ServerManager serverManager;
        if (ServerManagerUtil.servers.get(serverName) == null) {
            serverManager = new ServerManager(serverName, 0, false, 0, 0, 0.0F);
        } else if (ServerManagerUtil.servers.get(serverName).isOnline()) {
            try {
                serverManager = ServerManagerUtil.servers.get(serverName);
            } catch (Exception e) {
                serverManager = new ServerManager(serverName, 0, false, 0, 0, 0.0F);
            }
        } else {
            serverManager = ServerManagerUtil.servers.get(serverName);
        }

        ItemStack itemStack = new ItemStack(itemBuilder.getItemStack().getType(), itemBuilder.getItemStack().getAmount());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(color(replaceStringToServerManager(serverManager, itemBuilder.getDisplayName())));

        itemMeta.setLore(colorList(replaceListToServerManager(serverManager, itemBuilder.getLore())));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private static String replaceStringToServerManager(ServerManager serverManager, String stringToReplace) {
        DecimalFormat df = new DecimalFormat("00.0");
        return stringToReplace.replaceAll("%server%", serverManager.getServerName())
                .replaceAll("%last_update%", TimeUtil.format(System.currentTimeMillis() - serverManager.getLastUpdate()))
                .replaceAll("%online%", (serverManager.isOnline()) ? TorchMCLobby.getInstance().getInventories().getServerEnabled() : TorchMCLobby.getInstance().getInventories().getServerDisabled())
                .replaceAll("%players_online%", String.valueOf(serverManager.getPlayersOnline()))
                .replaceAll("%max_players%", String.valueOf(serverManager.getMaxPlayers()))
                .replaceAll("%tps%", String.valueOf(df.format(serverManager.getServerTps())));
    }

    private static List<String> replaceListToServerManager(ServerManager serverManager, List<String> listToReplace) {
        List<String> newList = new ArrayList<>();
        for (String string : listToReplace) {
            newList.add(replaceStringToServerManager(serverManager, string));
        }

        return newList;
    }
}
