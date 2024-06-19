package xyz.kozord.torchmclobby.config;


import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerItemBuilder extends OkaeriConfig {
    private String serverName;
    private ItemBuilder itemBuilderOnline;
    private ItemBuilder itemBuilderOffline;
}
