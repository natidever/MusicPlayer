/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musicplayer.newpackage;

import java.util.List;

/**
 *
 * @author Natnael
 */
public class Playlist_Data {
        private int id;
//    private String imagePath;
    private List<MusicMetaData> musics;
    private String name;

    public Playlist_Data(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

 

    public List<MusicMetaData> getMusics() {
        return musics;
    }

    public String getName() {
        return name;
    }

    
}
