package xyz.kozord.torchmclobby.modules.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {
    private static Map<CooldownType, Map<UUID, Long>> cooldownMap = new HashMap();

    public Cooldown() {
    }

    public static void put(CooldownType type, UUID uuid) {
        cooldownMap.putIfAbsent(type, new HashMap());
        ((Map)cooldownMap.get(type)).put(uuid, System.currentTimeMillis() + type.getDelay());
    }

    public static boolean isOnCooldown(CooldownType type, UUID uuid) {
        if (!((Map)cooldownMap.getOrDefault(type, new HashMap())).containsKey(uuid)) {
            return false;
        } else {
            return (Long)((Map)cooldownMap.get(type)).getOrDefault(uuid, 0L) >= System.currentTimeMillis();
        }
    }

    public static long getRemainingTime(CooldownType type, UUID uuid) {
        return !((Map)cooldownMap.getOrDefault(type, new HashMap())).containsKey(uuid) ? 0L : (Long)((Map)cooldownMap.get(type)).getOrDefault(uuid, 0L) - System.currentTimeMillis();
    }
}