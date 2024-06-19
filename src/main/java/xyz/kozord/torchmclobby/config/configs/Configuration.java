package xyz.kozord.torchmclobby.config.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import xyz.kozord.torchmclobby.config.Gui;
import xyz.kozord.torchmclobby.config.ItemBuilder;
import xyz.kozord.torchmclobby.config.PlayerInventory;
import xyz.kozord.torchmclobby.config.ServerItemBuilder;

import java.util.List;
import java.util.Map;

@Getter
public class Configuration extends OkaeriConfig {

    @Comment("Baza danych")
    private String link = "mongodb://localhost:27017/";
    private String databaseName = "minecraft";
    private String serverCollectionName = "servers";

    @Comment("Konfiguracja tego serwera")
    private String serverName = "lobby_1";

    @Comment("Co ile mają być update'owane informacje o tym serwerze.")
    private int dataInterval = 60;


    @Comment("Miejsce, na ktore gracz ma byc teleportowane przy kazdym dolaczeniu na serwer.")
    private Location defaultLocation = new Location(Bukkit.getWorld("world"), 75, 23, 36, 0.0F, 0.0F);

    @Comment("Ustawienia podwójnego skoku.")
    private boolean doubleJump = true;
    private long doubleJumpCooldownDelay = 1000;
    private double doubleJumpLaunch = 1.3;
    private double doubleJumpLaunchY = 1.2;
    private boolean doubleJumpSoundEnabled = true;
    private Sound doubleJumpSound = Sound.ENTITY_BAT_TAKEOFF;
    private float doubleJumpSoundVolume = 1.0F;
    private float doubleJumpSoundPitch = 1.0F;

    @Comment("Itemki, ktore maja zostac dodane graczowi przy dolaczeniu na serwer.")
    private PlayerInventory chooseMode = new PlayerInventory(new ItemBuilder(new ItemStack(Material.COMPASS),
            1,
            "&aWybierz tryb",
            List.of("&7Kliknij, aby wybrać tryb.")), 0);
    private PlayerInventory visibleModeTrue = new PlayerInventory(new ItemBuilder(new ItemStack(Material.GREEN_DYE),
            1,
            "&7Widoczność: &aON",
            List.of("&7Kliknij, aby zmienić tryb widoczności.")), 7);
    private PlayerInventory visibleModeFalse = new PlayerInventory(new ItemBuilder(new ItemStack(Material.RED_DYE),
            1,
            "&7Widoczność: &cOFF",
            List.of("&7Kliknij, aby zmienić tryb widoczności.")), 7);
    private PlayerInventory changeLobby = new PlayerInventory(new ItemBuilder(new ItemStack(Material.NETHER_STAR),
            1,
            "&aZmień lobby",
            List.of("&7Kliknij, aby zmienić lobby.")), 8);

    private long chatCooldown = 5000;
    private long visibilityChangeColdown = 5000;
    private long selectMenuCooldown = 5000;
    private long lobbyChangeCooldown = 5000;

    private Sound chooseModeSound = Sound.ENTITY_PLAYER_LEVELUP;
    private Sound changeLobbySound = Sound.UI_BUTTON_CLICK;
    private Sound visibilityChangeSound = Sound.ENTITY_CHICKEN_EGG;

    @Comment("Change lobby menu")
    private Gui changeLobbyInventoryGui = new Gui(
            "&cZmiana lobby",
            27,
            List.of(
                    Map.of(3, new ItemBuilder(new ItemStack(Material.APPLE), 7, "Testowy displaysdffdsa", List.of("Lorebsdv"))),
                    Map.of(10, new ItemBuilder(new ItemStack(Material.DIRT), 2, "&cTestowa nazwafdasafs", List.of("Lorebafasdf")))
            ),
            List.of(
                    Map.of(1, new ServerItemBuilder("lobby_5",
                            new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE), 1, "&cTestowy displayyyy", List.of("&aTPSy: %tps%")),
                            new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE), 10, "Testowy displayuuu", List.of("&aTPSy: %tps%")))
                    )
            )
    );
}
