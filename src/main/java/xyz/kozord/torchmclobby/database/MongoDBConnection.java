package xyz.kozord.torchmclobby.database;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.Bukkit;
import xyz.kozord.torchmclobby.config.configs.Configuration;
import xyz.kozord.torchmclobby.managers.ServerManager;

import java.util.ArrayList;
import java.util.List;

public class MongoDBConnection {

    private static Configuration configuration;

    public MongoDBConnection(Configuration configuration) {
        MongoDBConnection.configuration = configuration;
    }

    @SneakyThrows
    public static boolean connection() {
        try (MongoClient mongoClient = MongoClients.create(configuration.getLink())) {
            MongoDatabase database = mongoClient.getDatabase(configuration.getDatabaseName());
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().severe("Connection to MongoDB failed: " + e.getMessage());
            return false;
        }
    }

    public static List<ServerManager> getAllServers() {
        List<ServerManager> servers = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(configuration.getLink())) {
            MongoDatabase database = mongoClient.getDatabase(configuration.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection(configuration.getServerCollectionName());
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    ServerManager server = documentToServerManager(doc);
                    servers.add(server);
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to retrieve servers from MongoDB: " + e.getMessage());
        }
        return servers;
    }

    public static void updateOrCreateServer(ServerManager server) {
        try (MongoClient mongoClient = MongoClients.create(configuration.getLink())) {
            MongoDatabase database = mongoClient.getDatabase(configuration.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection(configuration.getServerCollectionName());
            Document doc = serverManagerToDocument(server);
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            collection.replaceOne(Filters.eq("_id", server.getServerName()), doc, options);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to update or create server in MongoDB: " + e.getMessage());
        }
    }

    private static ServerManager documentToServerManager(Document doc) {
        ServerManager server = new ServerManager();
        server.setServerName(doc.getString("serverName"));
        server.setLastUpdate(doc.getLong("lastUpdate"));
        server.setOnline(doc.getBoolean("online"));
        server.setPlayersOnline(doc.getInteger("playersOnline"));
        server.setMaxPlayers(doc.getInteger("maxPlayers"));

        Double serverTpsDouble = doc.getDouble("serverTps");
        float serverTps = serverTpsDouble != null ? serverTpsDouble.floatValue() : 0.0f;
        server.setServerTps(serverTps);

        return server;
    }

    private static Document serverManagerToDocument(ServerManager server) {
        Document doc = new Document();
        doc.append("_id", server.getServerName());
        doc.append("serverName", server.getServerName());
        doc.append("lastUpdate", server.getLastUpdate());
        doc.append("online", server.isOnline());
        doc.append("playersOnline", server.getPlayersOnline());
        doc.append("maxPlayers", server.getMaxPlayers());
        doc.append("serverTps", server.getServerTps());
        return doc;
    }
}
