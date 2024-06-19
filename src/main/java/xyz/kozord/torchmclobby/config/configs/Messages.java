package xyz.kozord.torchmclobby.config.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import xyz.kozord.torchmclobby.config.Time;
import xyz.kozord.torchmclobby.config.TimeBuilder;

import java.util.List;

@Getter
@Header({"Dostępne możliwości: TITLE, SUBTITLE, TITLE_SUBTITLE, CHAT, ACTIONBAR."})
public class Messages extends OkaeriConfig {

    private String whitelistKickMessage = "&cᴡʜɪᴛᴇʟɪꜱᴛᴀ ɴᴀ ᴅᴀɴʏᴍ ꜱᴇʀᴡᴇʀᴢᴇ ᴊᴇꜱᴛ ᴡᴌᴀ̨ᴄᴢᴏɴᴀ ᴢ ᴘᴏᴡᴏᴅᴜ: ᴘʀᴀᴄᴇ ᴛᴇᴄʜɴɪᴄᴢɴᴇ";

    private String noPermissionMessage = "&4Brak uprawnień! &8(&c%permission%&8)";

    private String vanishToggled = "&7Twój vanish został &awłączony&7.";
    private String vanishDisabled = "&7Twój vanish został &cwyłączony&7.";

    private List<String> chatOn = List.of("================================", "&7Czat został &awłączony&7.", "================================");
    private String chatActuallyOn = "&7Czat już jest &awłączony&7.";
    private List<String> chatOff = List.of("================================", "&7Czat został &cwyłączony&7.", "================================");
    private String chatActuallyOff = "&7Czat już jest &cwyłączony&7.";
    private List<String> chatCleared = List.of("================================", "&7Czat został &6wyczyszczony&7.", "================================");
    private String chatLocked = "&7Czat jest &cwyłączony&7.";
    private String chatUsage = "&7Użycie: &6/chat <on/off/clear>&7.";

    private TimeBuilder chatTimeConverter = new TimeBuilder(List.of(
            new Time(5000L, "sekund"),
            new Time(2000L, "sekundy"),
            new Time(1000L, "sekundę"),
            new Time(1L, "milisekund")));

    private String chatCooldownMessage = "&cOdczekaj &4%time% %value% &cprzed wysłaniem następnej wiadomości.";

    private String gamemodeChangeSelf = "&7Twój tryb gry został zmieniony na &6%gamemode%&7.";
    private String gamemodeChangedForSomeone = "&7Tryb gry &6%player% &7został zmieniony na &6%gamemode%&7.";

    @Comment("Jest to widoczne tylko wtedy, gdy gracz, na którym jest zmieniany tryb ma permisje.")
    private String gamemodeSomeoneChangedYou = "&7Gracz &6%player% &7zmienił twój tryb gry na &6%gamemode%&7.";
    private String gamemodeCommandUsage = "&7Użycie: &6/gamemode (0/1/2/3) [gracz]&7.";


    private String thisPlayerIsOffline = "&cPodany gracz jest &4offline&c.";

    private String flyEnabled = "&7Twoja możliwość latania została &awłączona&7.";
    private String flyDisabled = "&7Twoja możliwość latania została &cwyłączona&7.";

    private String flyEnabledForSomeone = "&7Możliwość latania dla &6%player% &7została &awłączona&7.";
    private String flyDisabledForSomeone = "&7Możliwość latania dla &6%player% &7została &cwyłączona&7.";

    private String flyEnabledForYou = "&7Twoja możliwość latania została &awłączona&7 przez &6%player%&7.";
    private String flyDisabledForYou = "&7Twoja możliwość latania została &cwyłączona&7 przez &6%player%&7.";

}
