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
public class Time extends OkaeriConfig {
    private Long minimumTime;
    private String value;
}
