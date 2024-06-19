package xyz.kozord.torchmclobby.command;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.command.commands.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class CommandMap {

    private final TorchMCLobby plugin;
    private SimpleCommandMap simpleCommandMap;

    public CommandMap(TorchMCLobby plugin) {
        this.plugin = plugin;
        this.setupSimpleCommandMap();
        Stream.of(new VanishCommand("vanish", List.of("v"), "torchmc.vanish.use"),
                        new ChatCommand("chat", null, "torchmc.chat.use"),
                        new GamemodeCommand("gamemode", List.of("gm"), "torchmc.gamemode.use"),
                        new FlyCommand("fly", List.of("flight"), "torchmc.fly.use"),
                        new TorchCommand("torchmclobby", List.of("tmc"), "torcmclobby.configuration"))
                .forEach(this::registerCommands);
    }

    private void registerCommands(CommandUse command) {
        this.simpleCommandMap.register(this.plugin.getDescription().getName(), command);
    }

    private void setupSimpleCommandMap() {
        SimplePluginManager spm = (SimplePluginManager) this.plugin.getServer().getPluginManager();
        Field f = null;

        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            Bukkit.getLogger().severe("Wystąpił błąd związany z pobieraniem commandMap'y: " + e.getMessage());
        }

        assert f != null;

        f.setAccessible(true);

        try {
            this.simpleCommandMap = (SimpleCommandMap) f.get(spm);
        } catch (IllegalAccessException e) {
            Bukkit.getLogger().severe("Wystąpił błąd związany z niepoprawnym dostępem podczas pobierania commandMap'y: " + e.getMessage());
        }
    }
}
