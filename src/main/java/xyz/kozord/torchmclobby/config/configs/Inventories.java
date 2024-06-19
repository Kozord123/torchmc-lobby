package xyz.kozord.torchmclobby.config.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.kozord.torchmclobby.config.Action;
import xyz.kozord.torchmclobby.config.Gui;
import xyz.kozord.torchmclobby.config.ItemBuilder;
import xyz.kozord.torchmclobby.config.ServerItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Inventories extends OkaeriConfig {

    private String serverEnabled = "&aON";
    private String serverDisabled = "&cOFF";

    @Comment("Placeholdery dla serverItemBuilder: %server_name%, %last_update%, %online%, %players_online%, %players_max%, %tps%")
    @Comment("Select menu")
    private List<Action> selectMenuActions = new ArrayList<>(List.of(new Action(1, "kolejka practise")));

    // Uncommented the changeLobbyActions list initialization
    private List<Action> changeLobbyActions = new ArrayList<>(List.of(new Action(1, "kolejka megadrop")));

    private Gui selectMenuGui = new Gui("&dWybierz tryb gejowski", 27, List.of(
            Map.of(1, new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE), 1, "Testowy display", List.of("Lore"))),
            Map.of(5, new ItemBuilder(new ItemStack(Material.DIRT), 64, "&cTestowa nazwa", List.of("Lore")))),
            List.of(Map.of(1, new ServerItemBuilder("lobby_1", new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE), 1, "&cTestowy display", List.of("&aTPS: %tps%")),
                    new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE), 1, "Testowy display", List.of("&aTPS: %tps%"))))));
}
