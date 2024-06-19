package xyz.kozord.torchmclobby.listener.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import xyz.kozord.torchmclobby.config.configs.Configuration;
import xyz.kozord.torchmclobby.modules.cooldown.CooldownType;

import java.util.UUID;

import static xyz.kozord.torchmclobby.modules.cooldown.Cooldown.isOnCooldown;
import static xyz.kozord.torchmclobby.modules.cooldown.Cooldown.put;

public class DoubleJump implements Listener {

    private boolean doubleJump;
    private double doubleJumpLaunch;
    private double doubleJumpLaunchY;
    private boolean doubleJumpSoundEnabled;
    private Sound doubleJumpSound;
    private float doubleJumpSoundVolume;
    private float doubleJumpSoundPitch;

    public DoubleJump(Configuration config) {

        this.doubleJump = config.isDoubleJump();
        this.doubleJumpLaunch = config.getDoubleJumpLaunch();
        this.doubleJumpLaunchY = config.getDoubleJumpLaunchY();
        this.doubleJumpSoundEnabled = config.isDoubleJumpSoundEnabled();
        this.doubleJumpSound = config.getDoubleJumpSound();
        this.doubleJumpSoundVolume = config.getDoubleJumpSoundVolume();
        this.doubleJumpSoundPitch = config.getDoubleJumpSoundPitch();
    }

    @EventHandler
    public void onPlayerToogleFlight(PlayerToggleFlightEvent event) {
        if (!this.doubleJump) return;
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.isFlying()) return;
        if (player.getWorld().getBlockAt(player.getLocation().subtract(0, 2, 0)).getType() == Material.AIR) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        UUID uuid = player.getUniqueId();
        if (isOnCooldown(CooldownType.DOUBLE_JUMP, uuid)) return;

        player.setVelocity(player.getLocation().getDirection().multiply(this.doubleJumpLaunch).setY(this.doubleJumpLaunchY));
        put(CooldownType.DOUBLE_JUMP, uuid);
        if (this.doubleJumpSoundEnabled) player.playSound(player.getLocation(), this.doubleJumpSound, this.doubleJumpSoundVolume, this.doubleJumpSoundPitch);
    }
}
