package musicplayer.newpackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class PlaylistDatabas {
    private static final String DB_NAME = "playlists.db";
    private static final String TABLE_NAME = "playlists";

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

     public static void createPlaylistTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     
     }
    
    
    
 public static int addPlaylist(String name) {
    String insertSQL = "INSERT INTO playlists(name) VALUES(?)";
    int playlistId = -1;

    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:playlists.db");
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                playlistId = generatedKeys.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return playlistId;
}

    
    
    
    
    
    
    
    



 public static List<Playlist_Data> loadPlaylistNames() {
    List<Playlist_Data> playlists = new ArrayList<>();
    String sql = "SELECT id, name FROM " + TABLE_NAME;

    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            playlists.add(new Playlist_Data(name, id));
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return playlists;
}

}
