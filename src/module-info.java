/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module MusicPlayer {
    requires javafx.swt;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires mp3agic;
    requires java.base;
    requires org.xerial.sqlitejdbc;
     exports musicplayer.newpackage;
//    opens com.sun.glass.utils to javafx.graphics;
   opens musicplayer.newpackage to javafx.graphics,  javafx.media,javafx.fxml;    
}
