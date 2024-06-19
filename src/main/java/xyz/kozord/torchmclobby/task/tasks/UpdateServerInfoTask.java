package xyz.kozord.torchmclobby.task.tasks;

import org.bukkit.Bukkit;
import xyz.kozord.torchmclobby.database.MongoDBConnection;
import xyz.kozord.torchmclobby.utils.ServerManagerUtil;
import xyz.kozord.torchmclobby.TorchMCLobby;
import xyz.kozord.torchmclobby.managers.ServerManager;
import xyz.kozord.torchmclobby.task.TaskUse;

public class UpdateServerInfoTask extends TaskUse {

    private int warnings = 0;

    @Override
    public void runTask() {
        if (MongoDBConnection.connection()) {
            warnings = 0;



            ServerManager thisServer = new ServerManager(TorchMCLobby.getInstance().getConfiguration().getServerName(),
                    System.currentTimeMillis(),
                    true,
                    Bukkit.getOnlinePlayers().size(),
                    Bukkit.getMaxPlayers(),
                    (float) Bukkit.getTPS()[0]);

            MongoDBConnection.updateOrCreateServer(thisServer);

            MongoDBConnection.getAllServers().forEach(doc -> {
                ServerManagerUtil.servers.put(doc.getServerName(), doc);
            });
        } else {
            warnings++;
            Bukkit.getLogger().severe("Nie udało się połączyć z bazą danych MongoDB " + warnings + "/3");
            if (warnings == 3) {
                Bukkit.getLogger().severe("Wyłączanie pluginu...");
                TorchMCLobby.getInstance().getServer().getPluginManager().disablePlugin(TorchMCLobby.getInstance());
            }
        }
    }
}
