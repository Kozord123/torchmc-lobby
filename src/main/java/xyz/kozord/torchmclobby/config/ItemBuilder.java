package xyz.kozord.torchmclobby.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemBuilder extends OkaeriConfig {
    private ItemStack itemStack;
    private int amount;
    private String displayName;
    private List<String> lore;
}
