package xyz.kozord.torchmclobby.managers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ServerManager {
    public String serverName;
    public long lastUpdate;
    public boolean online;
    public int playersOnline;
    public int maxPlayers;
    public float serverTps;
}
