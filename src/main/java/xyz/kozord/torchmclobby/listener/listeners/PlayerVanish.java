package xyz.kozord.torchmclobby.listener.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import xyz.kozord.torchmclobby.modules.vanish.Vanish;

import java.util.Objects;

public class PlayerVanish implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Vanish.vanished.forEach(hidden -> event.getPlayer().hidePlayer(Objects.requireNonNull(Bukkit.getPlayer(hidden))));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        Vanish.vanished.remove(player.getUniqueId());
    }
}
