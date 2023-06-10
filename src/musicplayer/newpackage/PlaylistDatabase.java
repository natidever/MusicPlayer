/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musicplayer.newpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PlaylistDatabase {
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
 
 
 public static void createPlaylistMusicTable() {
    String sql = "CREATE TABLE IF NOT EXISTS playlist_music (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " playlist_id INTEGER,\n"
            + " title TEXT ,\n"
            + " artist TEXT ,\n"
            + " album TEXT ,\n"
            + " duration TEXT ,\n"
            + " FOREIGN KEY (playlist_id) REFERENCES " + TABLE_NAME + " (id)\n"
            + ");";

    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public static void addMusicToPlaylist(int playlistId, MusicMetaData metaData) {
    String sql = "INSERT INTO playlist_music (playlist_id, title, artist, album, duration) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, playlistId);
        pstmt.setString(2, metaData.getTitle());
        pstmt.setString(3, metaData.getArtist());
        pstmt.setString(4, metaData.getAlbum());
        pstmt.setString(5, metaData.getDuration());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}




public static List<MusicMetaData> fetchMusicForPlaylist(int playlistId) {
    List<MusicMetaData> musicList = new ArrayList<>();
    String sql = "SELECT title, artist, album, duration FROM playlist_music WHERE playlist_id = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, playlistId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String title = rs.getString("title");
            String artist = rs.getString("artist");
            String album = rs.getString("album");
            String duration = rs.getString("duration");
            musicList.add(new MusicMetaData(title, artist, album, duration));
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return musicList;
}






}

