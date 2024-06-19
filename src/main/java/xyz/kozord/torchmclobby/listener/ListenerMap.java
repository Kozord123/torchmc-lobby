package xyz.kozord.torchmclobby.listener;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.kozord.torchmclobby.listener.listeners.DoubleJump;
import xyz.kozord.torchmclobby.listener.listeners.PlayerListeners;
import xyz.kozord.torchmclobby.listener.listeners.PlayerOffHandSwap;
import xyz.kozord.torchmclobby.listener.listeners.PlayerVanish;
import xyz.kozord.torchmclobby.config.configs.Configuration;

import java.util.stream.Stream;

public class ListenerMap {

    public ListenerMap(JavaPlugin plugin, Configuration configuration) {
        Stream.of(
                new PlayerListeners(),
                        new PlayerOffHandSwap(),
                        new DoubleJump(configuration),
                        new PlayerVanish())
                .forEach(playerListeners -> plugin.getServer().getPluginManager().registerEvents(playerListeners, plugin));
    }
}
