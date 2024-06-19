package xyz.kozord.torchmclobby.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.config.Gui;
import xyz.kozord.torchmclobby.config.configs.Inventories;
import xyz.kozord.torchmclobby.managers.ServerManager;

import static xyz.kozord.torchmclobby.utils.ChatUtil.color;

@Getter
public class InventoryUtil {

    private static Inventories inventories = TorchMCLobby.getInstance().getInventories();

    public static void openSelectMenuInventory(Player player) {
        player.openInventory(parseGuiToInventory(inventories.getSelectMenuGui()));
    }

    public static void openChangeLobbyInventory(Player player) {
        player.openInventory(parseGuiToInventory(TorchMCLobby.getInstance().getConfiguration().getChangeLobbyInventoryGui()));
    }

    private static Inventory parseGuiToInventory(Gui gui) {
        Inventory inventory = Bukkit.createInventory(null, gui.getSize(), color(gui.getName()));

        gui.getItems().forEach(items -> items.forEach((k, v) -> inventory.setItem(k, ItemParserUtil.parseItem(v))));
        gui.getServerItems().forEach(items -> items.forEach((k, v) -> {
            if (ServerManagerUtil.servers.containsKey(v.getServerName())) {
                ServerManager serverManager = ServerManagerUtil.servers.get(v.getServerName());
                String serverManagerName = serverManager.getServerName();
                if (!serverManager.isOnline()) {
                    inventory.setItem(k, ItemParserUtil.parseServerItem(serverManagerName, v.getItemBuilderOffline()));
                } else {
                    inventory.setItem(k, ItemParserUtil.parseServerItem(serverManagerName, v.getItemBuilderOnline()));
                }
            } else {
                inventory.setItem(k, ItemParserUtil.parseServerItem(v.getServerName(), v.getItemBuilderOffline()));
            }
        }));
        return inventory;
    }

}
