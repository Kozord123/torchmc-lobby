package xyz.kozord.torchmclobby.listener.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import xyz.kozord.torchmclobby.config.Action;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.config.configs.Configuration;
import xyz.kozord.torchmclobby.modules.cooldown.Cooldown;
import xyz.kozord.torchmclobby.modules.cooldown.CooldownType;
import xyz.kozord.torchmclobby.modules.messages.Chat;
import xyz.kozord.torchmclobby.modules.vanish.Vanish;
import xyz.kozord.torchmclobby.utils.InventoryUtil;
import xyz.kozord.torchmclobby.utils.ItemParserUtil;
import xyz.kozord.torchmclobby.utils.TimeConverterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static xyz.kozord.torchmclobby.utils.ChatUtil.color;

public class PlayerListeners implements Listener {

    Configuration configuration = TorchMCLobby.getInstance().getConfiguration();
    private List<UUID> hidden = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.teleport(TorchMCLobby.getInstance().getConfiguration().getDefaultLocation());

        player.setFoodLevel(20);
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.SURVIVAL);

        player.setFireTicks(0);

        player.getInventory().clear();


        player.getInventory().setItem(TorchMCLobby.getInstance().getConfiguration().getChooseMode().getContent(),
                ItemParserUtil.parseItem(TorchMCLobby.getInstance().getConfiguration().getChooseMode().getItemBuilder()));
        player.getInventory().setItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeTrue().getContent(),
                ItemParserUtil.parseItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeTrue().getItemBuilder()));
        player.getInventory().setItem(TorchMCLobby.getInstance().getConfiguration().getChangeLobby().getContent(),
                ItemParserUtil.parseItem(TorchMCLobby.getInstance().getConfiguration().getChangeLobby().getItemBuilder()));



        if (TorchMCLobby.getInstance().getConfiguration().isDoubleJump()) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.getPlayer().hasPermission("torchmclobby.chat")) {
            if (!Chat.isChatEnabled) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(color(TorchMCLobby.getInstance().getMessages().getChatLocked()));
            } else {
                if (Cooldown.isOnCooldown(CooldownType.CHAT, event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                    long remainingTime = Cooldown.getRemainingTime(CooldownType.CHAT, event.getPlayer().getUniqueId());
                    String timesuffix = TimeConverterUtil.getTime(remainingTime).getValue();

                    if (remainingTime >= 1000) remainingTime = remainingTime/1000;
                    String message = TorchMCLobby.getInstance().getMessages().getChatCooldownMessage();
                    message = message.replaceAll("%time%", String.valueOf(remainingTime));
                    message = message.replaceAll("%value%", timesuffix);
                    event.getPlayer().sendMessage(color(message));
                } else {
                    Cooldown.put(CooldownType.CHAT, event.getPlayer().getUniqueId());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("torchmclobby.blockbreak")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("torchmclobby.blockplace")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (!player.hasPermission("torchmclobby.inventoryclick")) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void whitelist(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            event.setKickMessage(color(TorchMCLobby.getInstance().getMessages().getWhitelistKickMessage()));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().isRightClick()) {
            if (player.getItemInHand().getType() == TorchMCLobby.getInstance().getConfiguration().getChooseMode().getItemBuilder().getItemStack().getType()) {
                if (!Cooldown.isOnCooldown(CooldownType.SELECT_MENU, player.getUniqueId())) {
                    InventoryUtil.openSelectMenuInventory(player);
                    player.playSound(player.getLocation(), configuration.getChooseModeSound(), 1.0F, 1.0F);
                    Cooldown.put(CooldownType.SELECT_MENU, player.getUniqueId());
                }
            } else if (player.getItemInHand().getType() == TorchMCLobby.getInstance().getConfiguration().getChangeLobby().getItemBuilder().getItemStack().getType()) {
                if (!Cooldown.isOnCooldown(CooldownType.CHANGE_LOBBY, player.getUniqueId())) {
                    InventoryUtil.openChangeLobbyInventory(player);
                    player.playSound(player.getLocation(), configuration.getChangeLobbySound(), 1.0F, 1.0F);
                    Cooldown.put(CooldownType.CHANGE_LOBBY, player.getUniqueId());
                }
            } else if (player.getItemInHand().getType() == TorchMCLobby.getInstance().getConfiguration().getVisibleModeTrue().getItemBuilder().getItemStack().getType()) {
                if (!Cooldown.isOnCooldown(CooldownType.VISIBILITY, player.getUniqueId())) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(pl);
                    }
                    player.playSound(player.getLocation(), configuration.getVisibilityChangeSound(), 1.0F, 1.0F);
                    Cooldown.put(CooldownType.VISIBILITY, player.getUniqueId());
                    player.getInventory().setItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeFalse().getContent(),
                            ItemParserUtil.parseItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeFalse().getItemBuilder()));
                }
            } else if (player.getItemInHand().getType() == TorchMCLobby.getInstance().getConfiguration().getVisibleModeFalse().getItemBuilder().getItemStack().getType()) {
                if (!Cooldown.isOnCooldown(CooldownType.VISIBILITY, player.getUniqueId())) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!Vanish.vanished.contains(pl.getUniqueId())) {
                            player.showPlayer(pl);
                        }
                    }
                    player.playSound(player.getLocation(), configuration.getVisibilityChangeSound(), 1.0F, 1.0F);
                    Cooldown.put(CooldownType.VISIBILITY, player.getUniqueId());
                    player.getInventory().setItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeTrue().getContent(),
                            ItemParserUtil.parseItem(TorchMCLobby.getInstance().getConfiguration().getVisibleModeTrue().getItemBuilder()));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getY() <= 0) {
            event.getPlayer().teleport(TorchMCLobby.getInstance().getConfiguration().getDefaultLocation());
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            e.setCancelled(true);
            if (e.getView().getTitle().equals(color(TorchMCLobby.getInstance().getInventories().getSelectMenuGui().getName()))) {
                int slot = e.getSlot();
                if (containsKey(TorchMCLobby.getInstance().getInventories().getSelectMenuActions(), slot)) {
                    String command = getAction(TorchMCLobby.getInstance().getInventories().getSelectMenuActions(), slot);
                    player.performCommand(Objects.requireNonNull(command));
                }
            } else if (e.getView().getTitle().equals(color(TorchMCLobby.getInstance().getConfiguration().getChangeLobbyInventoryGui().getName()))) {
                int slot = e.getSlot();
                e.setCancelled(true);
                if (containsKey(TorchMCLobby.getInstance().getInventories().getChangeLobbyActions(), slot)) {
                    String command = getAction(TorchMCLobby.getInstance().getInventories().getChangeLobbyActions(), slot);
                    player.performCommand(Objects.requireNonNull(command));
                }
            }
        }
    }

    private boolean containsKey(List<Action> list, int slot) {
        for (Action action : list) {
            if (action.getSlot() == slot) {
                return true;
            }
        }
        return false;
    }

    private String getAction(List<Action> list, int slot) {
        for (Action action : list) {
            if (action.getSlot() == slot) {
                return action.getCommand();
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        event.setCancelled(true);

    }
}
