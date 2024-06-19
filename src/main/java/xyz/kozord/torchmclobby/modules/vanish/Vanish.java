package xyz.kozord.torchmclobby.modules.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.kozord.torchmclobby.TorchMCLobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static xyz.kozord.torchmclobby.utils.ChatUtil.color;

public class Vanish {

    public static List<UUID> vanished = new ArrayList<>();

    public static void toggleVanish(Player player) {
        if (isVanished(player)) {
            vanished.remove(player.getUniqueId());
            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));

            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(color(TorchMCLobby.getInstance().getMessages().getVanishDisabled()));
        } else {
            vanished.add(player.getUniqueId());
            Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));

            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1));
            player.sendMessage(color(TorchMCLobby.getInstance().getMessages().getVanishToggled()));
        }
    }

    private static boolean isVanished(Player player) {
        return vanished.contains(player.getUniqueId());
    }
}
