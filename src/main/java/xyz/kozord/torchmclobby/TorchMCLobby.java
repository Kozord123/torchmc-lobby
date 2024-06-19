package xyz.kozord.torchmclobby;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.kozord.torchmclobby.command.CommandMap;
import xyz.kozord.torchmclobby.config.configs.Configuration;
import xyz.kozord.torchmclobby.config.configs.Inventories;
import xyz.kozord.torchmclobby.config.configs.Messages;
import xyz.kozord.torchmclobby.database.MongoDBConnection;
import xyz.kozord.torchmclobby.listener.ListenerMap;
import xyz.kozord.torchmclobby.task.tasks.UpdateServerInfoTask;
import xyz.kozord.torchmclobby.utils.InventoryUtil;

import java.io.File;

@Getter
public final class TorchMCLobby extends JavaPlugin {

    @Getter
    private static TorchMCLobby instance;
    private Configuration configuration;
    private Inventories inventories;
    private Messages messages;

    @Override
    public void onEnable() {


        instance = this;

        this.configuration = ConfigManager.create(Configuration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });

        this.inventories = ConfigManager.create(Inventories.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "inventory.yml"));
            it.saveDefaults();
            it.load(true);
        });

        this.messages = ConfigManager.create(Messages.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "message.yml"));
            it.saveDefaults();
            it.load(true);
        });

        new CommandMap(this);

        new ListenerMap(this, configuration);

        //inventory things
        new Inventories();
        new InventoryUtil();

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(getInstance(), new UpdateServerInfoTask(), 200L, configuration.getDataInterval() * 20L);

        new MongoDBConnection(configuration);


        if (!MongoDBConnection.connection()) {
            Bukkit.getLogger().severe("Nie udało się połączyć z bazą danych MongoDB!");
            Bukkit.getLogger().severe("Wyłączanie pluginu...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
