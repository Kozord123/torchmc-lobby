package xyz.kozord.torchmclobby.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Gui extends OkaeriConfig {
    private String name;
    private int size;
    private List<Map<Integer, ItemBuilder>> items;
    private List<Map<Integer, ServerItemBuilder>> serverItems;
}
