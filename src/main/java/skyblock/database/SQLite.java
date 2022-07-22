package skyblock.database;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import skyblock.utils.discord.log.core.Bot;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLite {

    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        connection = null;

        try {
            File file = new File("skyblock.db");

            if (!file.exists()) {
                file.createNewFile();
            }

            String url = "jdbc:sqlite:" +  file.getPath();
            connection = DriverManager.getConnection(url);

            System.out.println("Connected to SQLite database");

            statement = connection.createStatement();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from SQLite database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void onUpdate(String sql) throws SQLException {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ResultSet onQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addGuild(String guildId) {
        try {
            String sql = "INSERT INTO guilds (guild_id) VALUES ('" + guildId + "')";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static boolean checkGuild(String guildId) {
        String sql = "SELECT * FROM guilds WHERE guild_id = '" + guildId + "'";

        try (ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void deleteGuild(String guildId) {
        String sql = "DELETE FROM guilds WHERE guild_id = '" + guildId + "'";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
